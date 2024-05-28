import folium
from flask import Flask, render_template

app = Flask(__name__)

@app.route("/map")
def create_map():
    map = folium.Map(location=[-23.5489, -46.6388], tiles='CartoDB.Voyager')
    map.save("templates/map.html")
    return render_template("map.html")


if __name__ == '__main__':
    app.run(debug=True)