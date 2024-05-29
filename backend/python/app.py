import os
import folium
from folium.plugins import MarkerCluster
from flask import Flask, json, jsonify, render_template, request
import requests


app = Flask(__name__)

DATA_FILE = 'data/reports.json'

def load_data():
    if os.path.exists(DATA_FILE):
        with open(DATA_FILE, "r", encoding="utf-8") as data_file:
            return json.load(data_file)
    return []

def save_data(data):
    with open(DATA_FILE, "w") as data_file:
        json.dump(data, data_file, indent=4)

def is_near_water(lat, lon, radius):
    overpass_url = "http://overpass-api.de/api/interpreter"
    # Negocio horrendo de achar documentacao, meu deus. Se precisar mexer nisso aqui no futuro, boa sorte...
    overpass_query = f"""
    [out:json];
    (
      node["natural"="water"](around:{radius},{lat},{lon});
      way["natural"="water"](around:{radius},{lat},{lon});
      relation["natural"="water"](around:{radius},{lat},{lon});
      
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

@app.route("/map")
def create_map():
    map = folium.Map(location=[-23.5489, -46.6388], tiles='CartoDB.Voyager')
    marker_cluster = MarkerCluster().add_to(map)

    reports = load_data();
    for report in reports:
        popup_html = f"""
        <span>Registered by: {report["user"] if not "None" else "Anonymous"}</span>
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

    map.save("templates/map.html")
    return render_template("map.html")

@app.route("/report", methods=['POST'])
def report():
    data = request.get_json()
    if data == "None":
        return jsonify({"error": "Invalid body on request --> Expected JSON File"}), 400

    lat = float(data.get("lat"))
    lng = float(data.get("lng"))

    if not is_near_water(lat,lng,100): #USAR VARIAVEL NO FUTURO
         return jsonify({"error": f"Can't report outside the surroundings of water body"}), 400

    reports = load_data()

    reports.append(
        {
            "lat": lat,
            "lng": lng,
            "user": data.get("user"),
            "collected": bool(data.get("collected")),
            "description": data.get("description"),
            "severity": data.get("severity")
        }
    )
    
    save_data(reports)
    return jsonify({"message": f"JSON file was received succesfuly"}), 200


if __name__ == '__main__':
    app.run(debug=True)