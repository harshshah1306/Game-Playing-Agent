w=[0,1,3,4,5] #add 0 as we start from 1
v=[0,1,4,5,7]
maxweight=7
matrix=[]
for i in range(0,len(w)):
	temp=[]
	for j in range(maxweight+1):
		temp.append(0)
	matrix.append(temp)  #initialize the matrix to 0
for i in range(1,len(w)):
	for j in range(1,maxweight+1):
			if(j<w[i]):
				matrix[i][j]=matrix[i-1][j]
			else:
				matrix[i][j]=max(v[i]+matrix[i-1][j-w[i]],matrix[i-1][j])

print (matrix)