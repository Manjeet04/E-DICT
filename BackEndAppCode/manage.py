from flask import Flask
from flask_script import Manager
from flask_script import Server
from app.EDict.dict_views import *
from app import es


app = Flask(__name__)

init_dict()

from app.EDict.dict_views import dict_bp as my_router
app.register_blueprint(my_router, url_prefix='/dict')

manager = Manager(app)
manager.add_command("runpro", Server(host="127.0.0.1", port=5000, use_debugger=True))

if __name__ == '__main__':
    manager.run()