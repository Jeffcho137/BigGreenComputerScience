#Program that computes and prints out Professor Cormen's wealth at the end of this year
#Program that also computes how many border walls Professor Cormen can fund.
#Program that focuses on using while loops
#Author Jeffrey Cho, September 19, 2018

balance=1.00
counter = 1
YEAR=2018
COST_BORDER_WALL=21600000000

while counter <= YEAR:
    balance*=1.05
    counter+=1

print("After year", YEAR, "the balance is $" ,balance)
num_border_walls = balance//COST_BORDER_WALL
print("The number of border walls that can be funded is", num_border_walls)
