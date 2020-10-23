##a) - binomial distributionn = 3p = 1/2x=0:1:ndistribution=binopdf(x,n,p)##b)cumulativeDistribution = binocdf(x,n,p)##c) - e)p1=distribution(1,1) ##P(x=0)p2=1-distribution(1,2) ##P(x!=1)p3=cumulativeDistribution(3) ##P(x<=2)p4=cumulativeDistribution(2) ##P(x<2)p5=1-cumulativeDistribution(1) ##P(x>=1)p6=1-cumulativeDistribution(2)##p(x>1)


##f)
N =3;X=0;for i=1:N   toss=rand;   if toss>0.5     X=X+1;    endifendforX