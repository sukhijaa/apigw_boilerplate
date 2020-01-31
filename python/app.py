from flask import Flask
import py_eureka_client.eureka_client as eureka_client

app = Flask(__name__)

APP_PORT = 9000

@app.route('/')
def hello():
    return "Hello World! From PythonMS"

@app.route('/testPython')
def testRoute():
    return "Python Test MS"

if __name__ == '__main__':
    eureka_client.init(
            eureka_server="http://localhost:8761/eureka",
            app_name="python-ms",
            instance_port=APP_PORT
    )
    app.run(host='0.0.0.0', port=APP_PORT)
