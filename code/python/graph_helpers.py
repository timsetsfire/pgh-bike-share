import pandas as pd
import networkx as nx


def make_edges_df( edge_files, src, dst, weighted = True, allow_loops = False):
  """
  creates a dataframe containing edges specified in a list of files.  

  edge_files: list(str) => a list of files containing edges.  All edge information is discarded
  src: str => the column name containing the source vertex id
  dst: str => the column name containing the destination vertex id
  weighted: bool => if true, will aggregate the data, returning edges (src and dst)
  with total number of times the edge was observed.  

  Assumpiton
  * these edges are directed. 
  * the vertex ids can be cast to ints
  """

  edges_df = []
  for f in edge_files:
    edges_df.append( pd.read_csv(f, dtype=str))
  edges_df = pd.concat(edges_df).rename(columns = {src: "src", dst: "dst"})[["src","dst"]]
  edges_schema = {"src": int, "dst": int}
  try:
    edges_df = edges_df.dropna().astype(edges_schema)
  except Exception as e:
    print(e)
  if weighted:
    edges_df["count"] = 1
    edges_df = edges_df.groupby(["src", "dst"]).count().reset_index()
  if not allow_loops:
    edges_df = edges_df.query("`src` != `dst`")
  return edges_df.dropna()
    
def make_vertices_df(vertices_files, vertex_id, keep_floats, keep_strings = [], float_checks = None):
  """
  function to create a dataframe containing vertices specified in alist of files

  vertices_files: list(str) => files containing vertices
  vertex_id: str => column name containing the vertex id.  
  keep_floats: list(str) => list of float columns to keep in the dataframe
  keep_strings: list(str) => list of string columns to keep in the dataframe
  float_check: dict(dict) => keys in top level allign to float columns, keys in second
  should either be max and min.  This is used for some filtering.  not very robust

  Assumpiton 
  * the vertex ids can be cast to ints
  """

  vertices_df = []
  for f in vertices_files:
    vertices_df.append( pd.read_csv(f, dtype=str))
  vertices_df = pd.concat(vertices_df)
  vertices_df = vertices_df.loc[ vertices_df.duplicated() == False].rename(columns = { vertex_id: "id"})
  schema = [("id", int)]
  schema.extend( [ (kf, float) for kf in keep_floats])
  schema.extend( [ (ks, str) for ks in keep_strings])
  schema = dict(schema)
  vertices_df = vertices_df.astype(schema)
  if float_checks is not None:
    for f, checks in float_checks.items():
      vertices_df = vertices_df[vertices_df[f] >= checks["min"]]
      vertices_df = vertices_df[vertices_df[f] <= checks["max"]]
  vertices_df = vertices_df.loc[vertices_df["id"].duplicated() == False]
  return vertices_df.dropna()

def make_graph(vertex_id, src_id, dst_id, weight_col, vertices_df, edges_df):
  """
  used to make the networkx graph to represent the bike share data

  vertex_id: str => column name containing the vertex_id (vertices)
  src: str => the column name containing the source vertex id (edges)
  dst: str => the column name containing the destination vertex id (edges)
  weight_col: str => column name contaiing the weights of the edges
  vertices_df: pd.DataFrame => dataframe of vertices
  edges_df: pd.DataFrame => dataframe of edges
  """
  edges_df = pd.merge(vertices_df[["id"]], edges_df, how="inner", left_on = vertex_id, right_on = src_id).drop([vertex_id], axis=1)
  edges_df = pd.merge(vertices_df[["id"]], edges_df, how="inner", left_on = vertex_id, right_on = dst_id).drop([vertex_id], axis=1)
  edges_list = [( x[src_id], x[dst_id], x[weight_col]) for x in edges_df.to_dict(orient="records")]
  vertices_list = [ ( x[vertex_id], x ) for x in  vertices_df.dropna().to_dict(orient="records")]

  G = nx.Graph()
  G.add_nodes_from(vertices_list)
  G.add_weighted_edges_from(edges_list)
  return G