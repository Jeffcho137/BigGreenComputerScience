#Program that computes how long it takes for Professor Cormen to have a larger balance than Portia
#Program that utilizes the use of while loops
#Author Jeffrey Cho, September 19, 2018

cormen_balance = 1.00
portia_balance = 100000.00
year = 1

while cormen_balance < portia_balance:
    cormen_balance = cormen_balance * 1.05
    portia_balance = portia_balance * 1.04
    year = year + 1

print("Cormen's balance surpasses Portia's balance in year", year)
print("Cormen's balance is $", cormen_balance)
print("Portia's balance is $", portia_balance)
