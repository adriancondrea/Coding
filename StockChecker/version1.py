import smtplib
import getpass
import ssl
from time import sleep
import requests
from bs4 import BeautifulSoup


def get_page_html(url):
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) "
                      "Chrome/83.0.4103.116 Safari/537.36"}
    page = requests.get(url, headers=headers)
    return page.content


def get_items_in_stock(url):
    page_html = get_page_html(url)
    soup = BeautifulSoup(page_html, 'html.parser')
    out_of_stock_divs = soup.findAll("div", {"class": "shop-links"})
    stock_status = list()

    for elem in out_of_stock_divs:
        stock_status.append(elem.get_text().strip())
    current_stock = list()
    for product in stock_status:
        if product != 'Out of Stock':
            try:
                current_stock.append(product.split('\n')[1].strip())
            except Exception:
                current_stock.append(product)
    print("Current stock:" + str(current_stock))
    return current_stock


def send_mail(message):
    smtp_server = "smtp.gmail.com"
    port = 587  # For starttls
    # Create a secure SSL context
    context = ssl.create_default_context()

    # Try to log in to server and send email
    try:
        server = smtplib.SMTP(smtp_server, port)
        server.ehlo()  # Can be omitted
        server.starttls(context=context)  # Secure the connection
        server.ehlo()  # Can be omitted
        server.login(sender_email, password)
        server.sendmail(sender_email, receiver_email, message)
        server.quit()
    except Exception as e:
        # Print any error messages to stdout
        print(e)


def stock_checker(given_url):
    previous_stock = get_items_in_stock(given_url)
    # Testing:
    # send_mail("Stock changed!")
    while True:
        current_stock = get_items_in_stock(given_url)
        if len(current_stock) > len(previous_stock):
            send_mail("New Products Available!")
        else:
            print("Same stock!")
        previous_stock = current_stock
        sleep(60)


if __name__ == "__main__":
    sender_email = input("Sender email:")
    password = getpass.getpass("Password:")
    receiver_email = input("Receiver email: ")
    checking_frequency = int(input("Frequency to check: "))
    url = "https://www.amd.com/en/direct-buy/ro"
    stock_checker(url)
