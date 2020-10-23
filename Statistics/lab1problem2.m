  x = 0:3;
y1 = x.^5/10;
y2 = x .* sin(x);
y3 = cos(x);
plot(x,y1,'color','r', '--'); 
hold on;
plot(x,y2,'color','b', '-.'); 
hold on;
plot(x,y3,'color','y'); 
title("My graph")
legend("x^5/10", "x*sin(x)", "cos(x)")

figure
subplot(2,2,1)      
plot(x,y1)          
title('Subplot 1')
subplot(2,2,2)       
plot(x,y2)        
title('Subplot 2')
subplot(2,2,3)      
plot(x,y3)          
title('Subplot 3')

##plots   = [];
##%plots(1) = 
##plot(x,y1,'color','r', '--');
##
##%plots(2) = 
##plot(x,y2,'color','b', '-.'); 
##%plots(3) = 
##plot(x,y3,'color','y'); 
##figure(3);
##
####subplot(1,2,1), imshow(plots(1))
####subplot(1,2,2), imshow(plots(2))