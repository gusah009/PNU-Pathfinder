# -*- coding: utf-8 -*-

import selenium
from selenium import webdriver
# from selenium.webdriver import ActionChains

# from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By

from selenium.webdriver.support import expected_conditions as EC
# from selenium.webdriver.support.ui import Select
from selenium.webdriver.support.ui import WebDriverWait
from selenium.common.exceptions import TimeoutException
from time import sleep
from time import time



import platform

import pyautogui

officialStartURL = 'https://map.naver.com/v5/?c=14369598.7888581,4195396.7828313,16,0,0,0,dh&p=ghej0C2vIxkKwQOU_y1E3Q,-84.68,-8.03,80,Float'
myStartURL = 'https://map.naver.com/v5/?c=14369506.8255584,4195578.3207735,16,0,0,0,dh&p=NDE-B1jWiK7HXCZxo_yU-w,0,0,80,Float'


index = 0

delay = 30 # seconds

log = {}

DIR = './IMG/'

log_file = open("url_log_data.txt", "a")

if platform.system() == 'Darwin': # MAC
  driver = webdriver.Chrome(executable_path='./chromedriver 2')
elif platform.system() == 'Windows':
  driver = webdriver.Chrome(executable_path='chromedriver')
driver.maximize_window()
driver.get(url=myStartURL)

def removeElement():
  try:
    script = """
      btn_area = document.getElementsByClassName('btn_area');
      btn_close = document.getElementsByClassName('btn_close');
      btn_area[2].style.display = 'none';
      btn_close[0].style.display = 'none';
      bottom_area = document.getElementsByClassName('panorama_location_area')[0].parentNode.parentNode.childNodes;
      console.log(bottom_area);
      bottom_area[4].remove();
      bottom_area[3].remove();
      bottom_area[2].remove();
      bottom_area[1].remove();
    """
    driver.execute_script(script)
  except:
    print('removeElement Error\n')

def faceNorth():
  try:
    WebDriverWait(driver, delay).until(
      EC.presence_of_element_located((By.CLASS_NAME , 'btn_compass'))
    )
    script = """
      btn_compass = document.getElementsByClassName('btn_compass');
      btn_compass[0].click();
    """
    driver.execute_script(script)
    sleep(0.7)
  except:
    print('faceNorth Error\n')

def moveUpCamera():
  script = """
    btn_top = document.getElementsByClassName('btn_top');
    btn_top[0].click();
  """
  driver.execute_script(script)
  sleep(0.7)
  
def moveLeftCamera():
  script = """
    btn_left = document.getElementsByClassName('btn_left');
    btn_left[0].click();
  """
  driver.execute_script(script)
  sleep(0.7)
  
def goFront():
  # 모니터 전체 사이즈
  # 제 컴퓨터에 최적화되어있습니다!!
  width, height = pyautogui.size()
  pyautogui.moveTo(width / 2, height * 0.94)
  sleep(0.02)
  pyautogui.click()
  pyautogui.moveTo(10, 10)
  sleep(0.7)

def goBack():
  for _ in range(4):
    moveLeftCamera()
  goFront()

def lookBack():
  for _ in range(4):
    moveLeftCamera()
    sleep(0.1)

def initCrawler():
  WebDriverWait(driver, delay).until(
    EC.presence_of_element_located((By.CLASS_NAME , 'btn_area'))
  )
  WebDriverWait(driver, delay).until(
    EC.presence_of_element_located((By.CLASS_NAME , 'panorama_location_area'))
  )
  sleep(0.1)
  removeElement()
  faceNorth()
  moveLeftCamera()
  moveLeftCamera()
  goFront()
  global log
  # 쪽문
  log['14369445.9148015,4195757.4700585'] = True
  log['14369454.2751015,4195764.6360300'] = True
  # 북문
  log['14369453.0807729,4195936.6193436'] = True
  # 어느문
  log['14368732.9006470,4195452.9162740'] = True
  log['14368737.6779613,4195478.5943382'] = True
  log['14369437.5545015,4195172.2490607'] = True

  with open('url_log_data.txt', "r") as logs_url:
    for log_url in logs_url:
      loc_start = log_url.find('c=') + 2
      loc_end = log_url.find('&') - 12
      log[log_url[loc_start:loc_end]] = True

  # print(log)

def saveScreenShot(version):
  save_url = DIR + version +".png"
  driver.save_screenshot(save_url)
  

def wait_for_correct_current_url(prev, start, end):
  start_time = time()
  while True:
    curr = driver.current_url[start:end]
    if curr != prev:
      return True
    if time() - start_time > 3:
      return False

def backCurrUrl(curr_url, angle):
  start = curr_url.find('&p=') + 25
  end = curr_url.find('Float')
  curr_url = curr_url[:start] + angle + curr_url[end:]
  driver.get(curr_url)
  WebDriverWait(driver, delay).until(
    EC.presence_of_element_located((By.CLASS_NAME , 'btn_area'))
  )
  WebDriverWait(driver, delay).until(
    EC.presence_of_element_located((By.CLASS_NAME , 'panorama_location_area'))
  )
  sleep(0.1)
  removeElement()
  return curr_url

def findAngle():
  curr_url = driver.current_url
  start = curr_url.find('&p=') + 25
  end = curr_url.find('Float')
  return (curr_url[start:end], start, end)

def action(depth):
  curr_url =  driver.current_url
  loc_start = curr_url.find('c=') + 2
  loc_end = curr_url.find('&') - 12
  location = curr_url[loc_start : loc_end]
  if location in log:
    return location
  else:
    log[location] = True
    log_file.writelines(curr_url + "\n")

  for i in range(8):
    angle, ang_start, ang_end = findAngle()
    version = str(depth) + '_' + str(i) + '_' + location
    saveScreenShot(version)
    moveLeftCamera()
    wait_for_correct_current_url(curr_url, ang_start, ang_end)

  for i in range(8):
    angle, ang_start, ang_end = findAngle()
    goFront()
    canMove = wait_for_correct_current_url(location, loc_start, loc_end)
    if canMove:
      action(depth + 1)
      curr_url = backCurrUrl(curr_url, angle)
    moveLeftCamera()
    wait_for_correct_current_url(curr_url, ang_start, ang_end)

def main():
  initCrawler()

  try:
    action(1000)
  except TimeoutException:
    print("Loading took too much time!")

main()