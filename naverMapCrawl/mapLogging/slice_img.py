from pyproj import Proj, transform
import shutil
import os

os.chdir('../IMG')
img_list = os.listdir()
src = '../IMG'
dst = '../sampleIMG'


logs = open('../url_log_data.txt', 'r')

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
  if y2 >= 35.2299247 and x2 <= 129.0840231:
    if y2 <=35.2318527 and x2 >= 129.0812014:
      for img in img_list:
        if xy[0] + ',' + xy[1] in img:
          shutil.copy(src + '/' + img, dst)

logs.close()