#Towers of Hanoi
#def move_disk(disk_number, from_peg, to_peg):
#    print('Move disc', disk_number, 'from peg', from_peg, 'to peg', to_peg)

def solve_hanoi(n, start_peg, end_peg):
    spare_peg = 6 - end_peg - start_peg
    if n>0:
        solve_hanoi(n-1, start_peg, spare_peg)
        print('Move disc', n, 'from peg', start_peg, 'to peg', end_peg)
        #move_disk(n, start_peg, end_peg)
        solve_hanoi(n-1, spare_peg, end_peg)
print(solve_hanoi(4,1,2))