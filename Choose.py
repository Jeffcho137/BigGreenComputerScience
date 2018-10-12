#program to find the number of ways to choose a subset of k items from a set of n items
#Author Jeffrey Cho, October 10, 2018

def choose(n, k):

    if k == 0:                          #base case 1 for when k is 0
        return 1
    elif n == k:                        #base case 2 for when n is the same value as k
        return 1
    else:
        return choose((n-1),k) + choose((n-1),(k-1))

print(choose(51 , 5))