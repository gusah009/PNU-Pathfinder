from pyproj import Proj, transform
import json

logs = open('../url_log_data.txt', 'r')
locations = open('./public/loc_log_data.json', 'w')

# navermap
proj_NAVER = Proj(init='epsg:3857')
# WGS1984
proj_WGS1984 = Proj(init='epsg:4326')

locs = []
for log in logs:
  start = log.find('?c=') + 3
  end = log.find('&p=')
  xy = log[start:end].split(',')
  x, y = float(xy[0]), float(xy[1])
  x2,y2 = transform(proj_NAVER, proj_WGS1984, x, y)
  locs.append({'x': x2, 'y': y2})

json.dump(locs, locations)

logs.close()
locations.close()