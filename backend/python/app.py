import os
import folium
from flask import Flask, json, render_template

app = Flask(__name__)

DATA_FILE = 'data/reports.json'

def load_data():
    if os.path.exists(DATA_FILE):
        with open(DATA_FILE, "r") as data_file:
            return json.load(data_file)
    return []

@app.route("/map")
def create_map():
    map = folium.Map(location=[-23.5489, -46.6388], tiles='CartoDB.Voyager')

    reports = load_data();
    for report in reports:
        popup_html = f"""
        <span>Registered by: Anonymous</span>
        <ul>
            <li>Quantidade de lixo: {report["amount"]}</li>
            <li>Gravidade: {report["severity"]}</li>
        </ul>
        """
        folium.Marker(
            location=[report["lat"], report["lng"]],
            popup=popup_html
        ).add_to(map)

    map.save("templates/map.html")
    return render_template("map.html")


if __name__ == '__main__':
    app.run(debug=True)