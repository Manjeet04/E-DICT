from . import dict_bp
from flask import request, jsonify
from app import es
import uuid

#-----------------------------------------------MAJOR TILL HALF---------------------------------------------------------

"""from operator import itemgetter

def get_best_category(response):
    categories = {}
    print response
    for hit in response['hits']['hits']:
        score = hit['_score']
        category = hit['_source']['text_entry']
        print category
        if category not in categories:
            categories[category] = score
        else:
            categories[category] += score
    print categories
    if len(categories) > 0:
        sorted(categories.items(), key=lambda x: x[1], reverse=True)
        sortedCategories = sorted(categories.items(), key=itemgetter(1), reverse=True)
        categord = sortedCategories[0][0]
        return categord

@text_bp.route('/', methods=['GET'])
def texting():
    print "hello"
    return jsonify({"msg" : "welcome to text classification"})


def init_text():
    #es.indices.delete("shakespeare", ignore=[400, 404])
    body =   {

   "mappings": {
      "line": {
         "properties": {

             "line_number": {
                 "type": "text",
                 "term_vector" : "yes"


             },
             "speaker": {
                 "type": "text",
                 "term_vector": "yes"
             },
             "text_entry": {
                 "type": "text",
                 "term_vector": "yes"
             }


         }
      }
   }
}
    es.indices.create(index="shakespeare", body=body, ignore=400)



@text_bp.route('/add_try', methods=['POST'])
def addY():
    json_file = request.get_json()
    ids = str(uuid.uuid4())
    es.index(
        index="shakespeare", doc_type="line", id=ids,
        body=json_file
    )
    return jsonify({"msg" : "success add"})


@text_bp.route('/get_try', methods=['GET'])
def getY():
    query = {
        "query": {
            "more_like_this": {
                "fields": [
                   "line_number",
                    "speaker",
                    "text_entry"
                ],
                "like": "danger",
                "min_term_freq": 1,
                "max_query_terms": 25,
                "min_doc_freq" : 1
            }
        }
    }
    query = {

       "query":
           {
               "match_all" : {}
           }
    }
    result = es.search(index="shakespeare",  doc_type="line", body = query  )
    cate = get_best_category(result)
    return jsonify({"msg" : "success", "category" :  cate})"""

#------------------------------------------------------------------------------------------------------------------------


def init_dict():
    #es.indices.delete("dictionary", ignore=[400, 404])
    body =   {
        "settings": {

            "analysis": {
                "analyzer": {
                    "analyzer_shingle_snow": {
                        "tokenizer": "standard",
                        "filter": [
                            "standard",
                            "lowercase",
                            "filter_snow",
                            "filter_shingle"
                        ]
                    },
                    "analyzer_snow": {
                        "tokenizer": "keyword",
                        "filter": [
                            "standard",
                            "lowercase",
                            "filter_snow"
                        ]
                    }
                },
                "filter": {
                    "filter_shingle": {
                        "type": "shingle",
                        "max_shingle_size": 5,
                        "min_shingle_size": 2,
                        "output_unigrams": True
                    },
                    "filter_snow": {
                        "type": "snowball",
                        "language": "english"
                    }
                }
            }

        },

   "mappings": {
      "words": {
         "properties": {

             "word": {
                 "type": "string",
                 "index": "not_analyzed"

             },

             "meaning": {
                 "type": "string",
                 "analyzer": "analyzer_shingle_snow"

             },

             "synonym": {
                 "type": "string",
                 "index": "not_analyzed"

             },
             "antonym": {
                 "type": "string",
                 "index": "not_analyzed"

             },
             "example": {
                 "type": "string",
                 "index": "analyzer_shingle_snow"

             },
             "word_origin": {
                 "type": "string",
                 "index": "analyzer_shingle_snow"

             },
             "hit_points": {
                 "type": "float",
                 "index" : "not_analyzed"


             },


         }
      }
   }
}
    es.indices.create(index="dictionary",body = body,ignore = 400)


@dict_bp.route('/', methods=['GET'])
def test():
    return jsonify({"msg" : "welcome to my Dict"})


# add word to your dictonary
@dict_bp.route('/add_word', methods=['POST'])
def add_word():

    requestObject = request.get_json()
    ids = str(uuid.uuid4())
    data = requestObject
    data['id'] = ids
    data['hit_points'] = 1.0
    es.index(
        index="dictionary", doc_type="words", id=ids,
        body=data
    )
    return jsonify({"response": "success", "id": ids})


# images for crousal view
@dict_bp.route('/getHome', methods=['GET'])
def getHome():
    home_images = ["http://www.dailyexcelsior.com/wp-content/uploads/2016/09/219.jpg", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQeEYdV01e3ZHVo5td1olaCUnPGt7aRd-vsX2yH8H9ff3Hzpgeqlg",
                   "https://media.istockphoto.com/videos/animation-of-books-page-turning-perfect-for-learning-wisdom-and-book-video-id507824986?s=640x640"]
    tab_list = ["HOME","SMART_SEARCH","ADD_WORD"]
    return jsonify({"response": "success", "home_image_list": home_images,"tab_list" :tab_list})


# to d
@dict_bp.route('/most_searched', methods=['GET'])
def most_searched():
    try:
        dict_query = {
            "sort": [
                {"hit_points":{"order":"desc"}},
            ],
            "query":{

                "range":{
                    "hit_points":{"gte":0}
                }
            },

        }
        result = es.search(index="dictionary", doc_type="words", body=dict_query)
        res = result["hits"]["hits"]
        filterd_result = []
        wt = 1.0
        for re in res:
            fina = {}
            wt = wt-0.2
            fina[i]

            fin = re['_source']
            fina['word'] = fin['word']
            fina['meaning'] = fin['meaning']
            fina['synonym'] = fin['synonym']
            fina['antonym'] = fin['antonym']
            fina['word_origin'] = fin['word_origin']
            fina['example'] = fin['example']
            fina['hit_points'] = fin['hit_points']
            filterd_result.append(fina)
        return jsonify({"response":filterd_result})



    except Exception as e:
        print str(e)
        return jsonify({"msg":"error occurrred","error":str(e)})



@dict_bp.route('/match_all',methods=['GET'])
def match_all():
    try:
        dict_query ={
            "query":{
                "match_all":{}
            }
        }
        result = es.search(index="dictionary", doc_type="words", body=dict_query)
        res = result["hits"]["hits"]
        filtered_result = []
        for x in res:
            fin = x['_source']
            filtered_result.append(fin)
        return jsonify({"msg":"success","response":filtered_result})
    except Exception as e:
        print str(e)
        return jsonify({"msg":"error_occured","error":str(e)})







@dict_bp.route('/word_search',methods=['GET'])
def word_search():
    search=request.args['query']
    search = search.lower()
    try:
        res_list = []
        dict_query ={
            "query":{
                "bool":{
                    "must": {
                        "match_all": {}
                    },
                "filter":{
                    "bool":{
                    "must":
                        [{"term":{"word":search}}]
            }}}}
        }
        result = es.search(index="dictionary", doc_type="words", body=dict_query)
        res = result["hits"]["hits"]
        filterd_result = []
        for x in res:
            fina = {}
            fina['id']=x['_id']
            fin = x['_source']
            fina['word'] = fin['word']
            fina['meaning'] = fin['meaning']
            fina['synonym'] = fin['synonym']
            fina['antonym'] = fin['antonym']
            fina['word_origin'] = fin['word_origin']
            fina['example'] = fin['example']

            filterd_result.append(fina)
        return jsonify({"msg":"success","result":filterd_result})
    except Exception as e:
        print str(e)
        return jsonify({"msg":"error occured","error":str(e)})





@dict_bp.route('/search_query',methods=['GET'])
def search_query():
    search = request.args['query']
    field = request.args.get('fields', default="")



    try:

        p = field.split(',')

        dict_query = {


            "query": {

                "bool": {
                    "must": [

                        {"query_string": {

                            "query": search,
                            "fields": p,

                        }},


                    ],
                    "should":
                        [
                            {"multi_match":
                                {
                                    "query": search,
                                    "type": "most_fields",
                                    "fields": p

                                }},


                        ],


                }}}



        result = es.search(index="dictionary", doc_type="words", body=dict_query)



        res = result["hits"]["hits"]

        filterd_result = []
        for x in res:
            fina = {}
            fin = x['_source']
            fina['word'] = fin['word']
            fina['meaning'] = fin['meaning']
            fina['synonym'] = fin['synonym']
            fina['antonym'] = fin['antonym']
            fina['word_origin'] = fin['word_origin']
            fina['example'] = fin['example']

            filterd_result.append(fina)
        return jsonify({"msg":"success","respose":filterd_result})

    except Exception as e:
        print str(e)
        return jsonify({"msg":"error occured","error":str(e)})






