import os
import folium
from folium.plugins import MarkerCluster
from flask import Flask, json, jsonify, render_template, request


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
    user = data.get("user")
    collected = bool(data.get("collected"))
    description = data.get("description")
    severity = data.get("severity")

    reports = load_data()

    reports.append(
        {
            "lat": lat,
            "lng": lng,
            "user": user,
            "collected": collected,
            "description": description,
            "severity": severity
        }
    )
    
    save_data(reports)
    return jsonify({"message": f"JSON file was received succesfuly"}), 200


if __name__ == '__main__':
    app.run(debug=True)