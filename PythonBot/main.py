from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from time import sleep
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from constants import *


class bot:
    def __init__(self):
        self.driver = webdriver.Chrome()
        self.driver.get("https://google.com")

    def search(self, searchQuery):
        self.driver.get("https://google.com")
        searchBar = self.driver.find_element_by_name("q")
        searchBar.click()
        searchBar.send_keys(searchQuery)
        # enter key
        searchBar.send_keys(Keys.RETURN)
        print(self.driver.title)
        sleep(10)
        self.driver.quit()

    def netflixSearch(self, movieName):
        self.driver.get("https://www.netflix.com/browse")
        searchButton = self.driver.find_element_by_name("Search")
        searchButton.click()
        searchButton.send_keys(movieName)
        searchButton.send_keys(Keys.RETURN)

    def amazonSearch(self, productName):
        self.driver.get("https://www.amazon.com/")
        searchBar = self.driver.find_element_by_id("twotabsearchtextbox")
        searchBar.click()
        searchBar.send_keys(productName)
        searchBar.send_keys(Keys.ENTER)
        try:
            searchResults = WebDriverWait(self.driver, 10).until(EC.presence_of_all_elements_located((By.CSS_SELECTOR, "a-size-base-plus a-color-base a-text-normal")))
            print(searchResults)
            self.driver.quit()
        except:
            self.driver.quit()

    def pbinfoList(self):
        self.driver.get("https://www.pbinfo.ro/")
        try:
            problemsButton = WebDriverWait(self.driver, 10).until(EC.presence_of_all_elements_located(
                By.XPATH, "/html/body/div[2]/nav/div/div[2]/ul[1]/li[2]/a"))
            problemsButton.click()
            selectedProblems = WebDriverWait(self.driver, 10).until(EC.presence_of_all_elements_located(
                By.XPATH, "/html/body/div[2]/nav/div/div[2]/ul[1]/li[2]/ul/li[1]/a"))
            selectedProblems.click()
            operators = WebDriverWait(self.driver, 10).until(EC.presence_of_all_elements_located((By.XPATH, "/html/body/div[2]/div[5]/div/div[6]/div[1]/div/div[2]/ul/li["
                                                      "1]/a/span")))
            operators.click()
        except:
            self.driver.quit()


myBot = bot()
'''
file = open("searches.txt", 'r')
for line in file:
    myBot.search(line)
'''
myBot.search("maimuta")
