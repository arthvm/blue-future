import os
from flask_cors import CORS
import folium
from folium.plugins import MarkerCluster
from flask import Flask, jsonify, render_template, request
import requests
from appwrite.id import ID
from appwrite.client import Client
from appwrite.services.databases import Databases
from appwrite.services.storage import Storage

APPWRITE_ENDPOINT = os.getenv("APPWRITE_ENDPOINT")
APPWRITE_PROJECT_ID = os.getenv("APPWRITE_PROJECT_ID")
APPWRITE_API_KEY = os.getenv("APPWRITE_API_KEY")
DATABASE_ID = os.getenv("DATABASE_ID")
COLLECTION_ID = os.getenv("COLLECTION_ID")

app = Flask(__name__)
CORS(app)

client = Client()
client.set_endpoint(APPWRITE_ENDPOINT)
client.set_project(APPWRITE_PROJECT_ID)
client.set_key(APPWRITE_API_KEY)

database = Databases(client)
storage = Storage(client)

def load_data():
    try:
        reports = []
        response = database.list_documents(DATABASE_ID, COLLECTION_ID)
        for document in response["documents"]:
            reports.append(document)
        return reports
    except Exception as e:
        print(f"Erro ao carregar dados do Appwrite: {e}")
        return []

def save_data(data):
    try:
        database.create_document( 
        DATABASE_ID,
        COLLECTION_ID,
        document_id=ID.unique(),
        data=data)
    except Exception as e:
        print(f"Erro ao salvar dados no Appwrite: {e}")

def is_near_water(lat, lon, radius):
    overpass_url = "http://overpass-api.de/api/interpreter"
    # Negocio horrendo de achar documentacao, meu deus. Se precisar mexer nisso aqui no futuro, boa sorte...
    overpass_query = f"""
    [out:json];
    (
      node["natural"="water"](around:{radius},{lat},{lon});
      way["natural"="water"](around:{radius},{lat},{lon});
      relation["natural"="water"](around:{radius},{lat},{lon});

      way["natural"="coastline"](around:{radius},{lat},{lon});
      
      node["water"="lake"](around:{radius},{lat},{lon});
      way["water"="lake"](around:{radius},{lat},{lon});
      relation["water"="lake"](around:{radius},{lat},{lon});
      
      node["waterway"="river"](around:{radius},{lat},{lon});
      way["waterway"="river"](around:{radius},{lat},{lon});
      relation["waterway"="river"](around:{radius},{lat},{lon});

      node["waterway"="stream"](around:{radius},{lat},{lon});
      way["waterway"="stream"](around:{radius},{lat},{lon});
      relation["waterway"="stream"](around:{radius},{lat},{lon});
      
      node["landuse"="reservoir"](around:{radius},{lat},{lon});
      way["landuse"="reservoir"](around:{radius},{lat},{lon});
      relation["landuse"="reservoir"](around:{radius},{lat},{lon});
      
      node["natural"="pond"](around:{radius},{lat},{lon});
      way["natural"="pond"](around:{radius},{lat},{lon});
      relation["natural"="pond"](around:{radius},{lat},{lon});
      
      node["waterway"="canal"](around:{radius},{lat},{lon});
      way["waterway"="canal"](around:{radius},{lat},{lon});
      relation["waterway"="canal"](around:{radius},{lat},{lon});
    );
    out body;
    >;
    out skel qt;
    """
    
    response = requests.get(overpass_url, params={'data': overpass_query})
    if not response.json()['elements']:
        return False
    
    return True

@app.route("/")
def create_map():
    map = folium.Map(
        location=[-23.5489, -46.6388],
        tiles='CartoDB.Voyager',
        min_zoom = 4,
        max_bounds = True,
        zoom_control = False
    )
    marker_cluster = MarkerCluster().add_to(map)

    reports = load_data();
    for report in reports:
        if report["collected"]:
            continue

        popup_html = f"""
        <span>Registered by: {"Anonymous" if report["user"] == None else report["user"]}</span>
        <ul>
            <li>Descrição: {report["description"]}</li>
            <li>Gravidade: {report["severity"]}</li>
        </ul>
        """

        match report["severity"]:
            case "high":
                severity_color = "red"
            case "medium":
                severity_color = "orange"
            case _:
                severity_color = "green"

        folium.Marker(
            location=[report["lat"], report["lng"]],
            popup=popup_html,
            icon=folium.Icon(
                color=severity_color
            )
        ).add_to(marker_cluster)

    return map.get_root().render()

@app.route("/report", methods=['POST'])
def report():
    data = request.get_json()
    if data == "None":
        return jsonify({"error": "Invalid body on request --> Expected JSON File"}), 400

    lat = float(data.get("lat"))
    lng = float(data.get("lng"))

    if not is_near_water(lat,lng,100): #USAR VARIAVEL NO FUTURO
         return jsonify({"error": f"Can't report outside the surroundings of water body"}), 400

    report = (
        {
            "lat": lat,
            "lng": lng,
            "user": data.get("user"),
            "collected": bool(data.get("collected")),
            "description": data.get("description"),
            "severity": data.get("severity")
        }
    )
    
    save_data(report)
    return jsonify({"message": f"JSON file was received succesfuly"}), 200


if __name__ == '__main__':
    app.run(debug=True)
