# scan.py
# Performs inclusive and exclusive scan operations on a list.

def plus(a, b):
    return a + b

def times(a, b):
    return a * b

# Perform an inclusive scan operation on my_list, given an operation.
# When done, my_list[i] should contain the result of the operation
# performed on my_list[0] through my_list[i], and my_list[0] should
# remain unchanged.

def inclusive_scan(my_list, operation):
    for i in range(1, len(my_list)):
        my_list[i] = operation(my_list[i-1],my_list[i])     #
    return my_list

# Perform an exclusive scan operation on my_list, given an operation
# and the identity for the operation.  When done, my_list[i] should
# contain the result of the operation performed on my_list[0] through
# my_list[i-1], and my_list[0] should contain the identity.
def exclusive_scan(my_list, operation, identity):
    temp1 = identity                #creation of first stored variable --> index 0 value for list
    for i in range(1, len(my_list)):
        temp2 = my_list[i]          #creation of second stored variable in list that is about to be changed
        my_list[i] = operation(temp1, my_list[i-1])
        temp1 = temp2               #reassignment of first stored variable to continue the loop
    my_list[0] = identity           #assignment of the identity value at beginning of final list
    return my_list

numbers = [3, 6, 2, 1, 4, 7]
print("The list: ", numbers)
exclusive_scan(numbers, plus, 0)
print("After an exclusive plus-scan:", numbers)

numbers = [3, 6, 2, 1, 4, 7]
print("The list: ", numbers)
inclusive_scan(numbers, plus)
print("After an inclusive plus-scan:", numbers)

numbers = [2, 4, 2, 6, 2]
print("The list: ", numbers)
exclusive_scan(numbers, times, 1)
print("After an exclusive times-scan:", numbers)

numbers = [2, 4, 2, 6, 2]
print("The list: ", numbers)
inclusive_scan(numbers, times)
print("After an inclusive times-scan:", numbers)
