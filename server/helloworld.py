#!/usr/bin/env python
#
# Copyright 2009 Facebook
#
# Licensed under the Apache License, Version 2.0 (the "License"); you may
# not use this file except in compliance with the License. You may obtain
# a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
# WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations
# under the License.

import tornado.httpserver
import tornado.ioloop
import tornado.options
import tornado.web

import dbox_xml
import file_process

from tornado.options import define, options

define("port", default=8888, help="run on the given port", type=int)


class MainHandler(tornado.web.RequestHandler):
    def get(self):
        #file = open('myfile.txt')
        #lines = file.readlines()
        #s = ""
        #for line in lines:
        #    s = s + line
        path = self.request.uri
        print(path)
        process_path = path[1:]
        if process_path == '':
            process_path = '.'
        if (file_process.isDir(process_path)):
            dirlist = dbox_xml.list_file_dir(process_path)
            filename = dbox_xml.createXML(dirlist, "hrl.xml")
            con = dbox_xml.read_xml_file(filename)
            print(con)
            self.write(con)
        else:
            content = file_process.readFile(process_path)
            self.write(content)


    def post(self):
        print(self.request.files)
        #print(self.request.files[filename])
        file_name = self.request.files['attachment'][0]['filename']
        file_content = self.request.files['attachment'][0]['body']
        #original_fname = file1['filename']
        #extension = os.path.splitext(original_fname)[1]
        #fname = ''.join(random.choice(string.ascii_lowercase + string.digits) for x in range(6))
        #final_filename= fname+extension
        print(file_name)
        path = self.request.uri
        print(path)
        process_path = path[1:]

        file_process.writeFile(process_path, file_content)


class MyFormHandler(tornado.web.RequestHandler):
    def get(self):
        self.write('<html><body><form action="/form" method="post">'
                   '<input type="text" name="message">'
                   '<input type="submit" value="Submit">'
                   '</form></body></html>')

    def post(self):
        self.set_header("Content-Type", "text/plain")
        self.write("You wrote " + self.get_argument("message"))

class MyRegisterHandler(tornado.web.RequestHandler):
    
    def get(self):
        self.write('you request get')

    def _make_xml(self,username,password):
        
        doc = Document();
        node = doc.createElement('Node')
        node1 = doc.createElement('UserName')
        node1.appendchild(doc.createTextNode(username))
        
        node2 = doc.createElement('PassWord')
        node2.appendchild(doc.createTextNode(password))
        
        node.appendchild(node1)
        node.appendchild(node2)
        doc.appendChild(node)
        return doc
        

    def post(self):
        UserName = self.get_argument("userName",'')
        PassWord = self.get_argument('passWord','')
        
        with open(".passwd", "a") as f:
            f.write(UserName+",")
            f.write(PassWord+'\n')
        #f.close()
        
        
        self.set_header("Content-Type", "text/plain")
        self.write("You wrote " + self.get_argument("userName",''))


        

def main():
    tornado.options.parse_command_line()
    application = tornado.web.Application([
        (r"/*", MainHandler),
        (r"/form", MyFormHandler),
        (r"/register", MyRegisterHandler)
    ])
    http_server = tornado.httpserver.HTTPServer(application)
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()


if __name__ == "__main__":
    main()
