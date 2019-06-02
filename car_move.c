//gcc car_move.c -o car `pkg-config --cflags --libs gtk+-3.0` -lm

#include <cairo.h>
#include <gtk/gtk.h>
#include <string.h>
#include <math.h>

static void do_drawing(cairo_t *);

int lane1[167];
int lane2[167];
int lane3[167];
int lane4[167];
int lane5[167];
int lane6[167];

int k=0;

GtkWidget *window;
GtkWidget *darea;

struct {
  int count;
  double coordx[100];
  double coordy[100];
} glob;

static gboolean on_draw_event(GtkWidget *widget, cairo_t *cr, 
    gpointer user_data)
{
  do_drawing(cr);

  return FALSE;
}

static void do_drawing(cairo_t *cr)
{


  cairo_set_source_rgb(cr, 0, 0, 0);
  cairo_set_line_width(cr, 0.5);


  cairo_move_to(cr, 1870, 50);
  cairo_line_to(cr, 1870-98, 50+28);
  cairo_line_to(cr, 1870-98-492, 50+28+328);
  cairo_line_to(cr, 1870-98-492-216, 50+28+328+432);
  cairo_line_to(cr, 1870-98-492-316, 50+28+328+632);

  cairo_move_to(cr, 1880, 60);
  cairo_line_to(cr, 1880-98, 60+28);
  cairo_line_to(cr, 1880-98-492, 60+28+328);
  cairo_line_to(cr, 1880-98-492-216, 60+28+328+432);
  cairo_line_to(cr, 1880-98-492-316, 60+28+328+632);

  cairo_move_to(cr, 1890, 70);
  cairo_line_to(cr, 1890-98, 70+28);
  cairo_line_to(cr, 1890-98-492, 70+28+328);
  cairo_line_to(cr, 1890-98-492-216, 70+28+328+432);
  cairo_line_to(cr, 1890-98-492-316, 70+28+328+632);
  cairo_stroke(cr);    
  cairo_set_line_width(cr, 2);


  cairo_move_to(cr, 1900, 80);
  cairo_line_to(cr, 1900-98, 80+28);
  cairo_line_to(cr, 1900-98-492, 80+28+328);
  cairo_line_to(cr, 1900-98-492-216, 80+28+328+432);
  cairo_line_to(cr, 1900-98-492-316, 80+28+328+632);



  cairo_stroke(cr);    

  
  cairo_set_line_width(cr, 0.5);

  cairo_move_to(cr, 1910, 90);
  cairo_line_to(cr, 1910-98, 90+28);
  cairo_line_to(cr, 1910-98-492, 90+28+328);
  cairo_line_to(cr, 1910-98-492-216, 90+28+328+432);
  cairo_line_to(cr, 1910-98-492-316, 90+28+328+632);

  cairo_move_to(cr, 1920, 100);
  cairo_line_to(cr, 1920-98, 100+28);
  cairo_line_to(cr, 1920-98-492, 100+28+328);
  cairo_line_to(cr, 1920-98-492-216, 100+28+328+432);
  cairo_line_to(cr, 1920-98-492-316, 100+28+328+632);

  cairo_move_to(cr, 1930, 110);
  cairo_line_to(cr, 1930-98, 110+28);
  cairo_line_to(cr, 1930-98-492, 110+28+328);
  cairo_line_to(cr, 1930-98-492-216, 110+28+328+432);
  cairo_line_to(cr, 1930-98-492-316, 110+28+328+632);
  cairo_stroke(cr);

//part 1
/*
  cairo_stroke(cr);  
  cairo_arc(cr, 740, 40+(7), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-(7), 40+(8), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-(14), 40+(9), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-(21), 40+(10), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-(28), 40+(11), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-(35), 40+(12), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-(42), 40+(13), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-(49), 40+(14), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-(56), 40+(15), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-(63), 40+(16), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-(70), 40+(17), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-(77), 40+(18), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-(84), 40+(19), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-(91), 40+(20), 3, 0, 2*M_PI);
  cairo_stroke(cr);    

// part 2
  cairo_stroke(cr);  
  cairo_arc(cr, 740-97, 40+(23), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-97-(7), 40+(26), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-97-(14), 40+(29), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-97-(21), 40+(32), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-97-(28), 40+(35), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-97-(35), 40+(38), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-97-(42), 40+(41), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-97-(49), 40+(44), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-97-(56), 40+(47), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-97-(63), 40+(50), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-97-(70), 40+(53), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-97-(77), 40+(56), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-97-(84), 40+(59), 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-97-(91), 40+(62), 3, 0, 2*M_PI);
  cairo_stroke(cr);

// part 3
  cairo_stroke(cr);  
  cairo_arc(cr, 740-496, 40+195-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(5), 40+(200)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);  
  cairo_arc(cr, 740-496-(10), 40+(205)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(15), 40+(210)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(20), 40+(215)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(25), 40+(220)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(30), 40+(225)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(35), 40+(230)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(40), 40+(235)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(45), 40+(240)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(50), 40+(245)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(55), 40+(250)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(60), 40+(255)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(65), 40+(260)-4, 3, 0, 2*M_PI); //89
  cairo_stroke(cr);  

  cairo_arc(cr, 740-496-70, 40+265-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(75), 40+(270)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(80), 40+(275)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(85), 40+(280)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(90), 40+(285)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(95), 40+(290)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(100), 40+(295)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(105), 40+(300)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(110), 40+(305)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(115), 40+(310)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(120), 40+(315)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(125), 40+(320)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(130), 40+(325)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);    
  cairo_arc(cr, 740-496-(135), 40+(330)-4, 3, 0, 2*M_PI);
  cairo_stroke(cr);

*/

  int tmp1=14;
	int tmp2=0;
	int tmp3=76;

	int i2=1;
	int i3=1;

  for(int i=0; i<167; i++)
	{
		if(i<14)
		{			
			if(lane1[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1870-(7*(i))+12, 50+(2*i)+3, 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}		

			if(lane2[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1880-(7*(i))+12, 60+(2*i)+3, 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}


			if(lane3[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1890-(7*(i))+12, 70+(2*i)+3, 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}


			if(lane4[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1900-(7*(i))+12, 80+(2*i)+3, 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}


			if(lane5[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1910-(7*(i))+12, 90+(2*i)+3, 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}

		

			if(lane6[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1920-(7*(i))+12, 100+(2*i)+3, 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}
		}
		else if(i>13 && i<76)
		{
			if(i>40)
				tmp2=3;
			if(lane1[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1870-97-(6*(i2))+12, 50+17+(4*i2)+7, 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}		

			if(lane2[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1880-97-(6*(i2))+12, 60+17+(4*i2)+7, 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}


			if(lane3[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1890-97-(6*(i2))+12, 70+17+(4*i2)+7, 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}


			if(lane4[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1900-97-(6*(i2))+12, 80+17+(4*i2)+7, 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}


			if(lane5[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1910-97-(6*(i2))+12, 90+17+(4*i2)+7, 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}

		

			if(lane6[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1920-97-(6*(i2))+12, 100+17+(4*i2)+7, 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}
			i2++;
		}
		else if(i>75)
		{
			if(lane1[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1870-97-470-(3*i3)-10, 50+17+329+(6*i3), 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}		

			if(lane2[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1880-97-470-(3*i3)-10, 60+17+329+(6*i3), 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}


			if(lane3[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1890-97-470-(3*i3)-10, 70+17+329+(6*i3), 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}


			if(lane4[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1900-97-470-(3*i3)-10, 80+17+329+(6*i3), 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}


			if(lane5[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1910-97-470-(3*i3)-10, 90+17+329+(6*i3), 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}

		

			if(lane6[i]==1)
			{
			//	printf("1, i=%d\n", i); 
  			cairo_arc(cr, 1920-97-470-(3*i3)-10, 100+17+329+(6*i3), 3, 0, 2*M_PI);
  			cairo_stroke(cr);  
			}
			i3++;
		}
		
	}   
  
  cairo_stroke(cr);   
  //puts("done");

}

static gboolean clicked(GtkWidget *widget, GdkEventButton *event, gpointer user_data)
{
    if (event->button == 1) {
        glob.coordx[glob.count] = event->x;
        glob.coordy[glob.count++] = event->y;
    }

    if (event->button == 3) {
      //  gtk_widget_queue_draw(widget);
    }

    return TRUE;
}

gboolean car_movement()
{

  if(k==150)
		return FALSE;

  char file_num1 = '0';
  char file_num2 = '0';
  char file_num3 = '0';

  char filename3[15] = "test_files/000";

  FILE *file;

  char c;
  int count = 1;
  int i=0;
	int tmp1;
	int tmp2;
	int tmp3;
//int k=65;




  if(k<10)
	{
			file_num1=k+48;
			filename3[11]='0';
			filename3[12]='0';
			filename3[13]=file_num1;
			file = fopen(filename3, "r");
	}
	else if (k<100)
	{
			tmp1=k%10; //64%10=4
			file_num2=tmp1+48;
			
			tmp2=(k-tmp1)/10;
			file_num1=tmp2+48;

			filename3[11]='0';
			filename3[12]=file_num1;
			filename3[13]=file_num2;
			file = fopen(filename3, "r");
	}
	else if (k<150)
	{		
			tmp2=k%100; //147%100=47 // 100%100 = 0
			tmp3=tmp2%10; //47%10 = 7   
	
			tmp1=(k-tmp2)/100;	
			tmp2=(tmp2-tmp3)/10;	

			file_num1=tmp1+48;
			file_num2=tmp2+48;
			file_num3=tmp3+48;

			filename3[11]=file_num1;
			filename3[12]=file_num2;
			filename3[13]=file_num3;
			file = fopen(filename3, "r");
	}

  if (file == NULL)
  {
		printf("k=%d, filename3=%s, nie ma pliku\n", k, filename3);
	  return 1;
  }

  count = 1;
  while((c = fgetc(file)) != EOF)
  {
  
		if(c==10)
		{
			printf("count: %d\n ", count);
			count++;
			i = 0;
  		printf("\n");
		}
		if(c!=10 && c!=13 && i<168)
		{
			switch(count)
			{
				case 1:
					lane1[i] = c-48;
					i++;
					break;
				case 2:
					lane2[i] = c-48;
					i++;
					break;
				case 3:
					lane3[i] = c-48;
					i++;
					break;
				case 4:
					lane4[i] = c-48;
					i++;
					break;
				case 5:
					lane5[i] = c-48;
					i++;
					break;
				case 6:
					lane6[i] = c-48;
					i++;
					break;
			}
		}

  }
	k++;

  gtk_widget_queue_draw(darea);
  return TRUE;
}

int main(int argc, char *argv[])
{
  
  glob.count = 0;

  gtk_init(&argc, &argv);

  window = gtk_window_new(GTK_WINDOW_TOPLEVEL);

  darea = gtk_drawing_area_new();
  gtk_container_add(GTK_CONTAINER(window), darea);
 
  gtk_widget_add_events(window, GDK_BUTTON_PRESS_MASK);

  
  g_signal_connect(G_OBJECT(darea), "draw", G_CALLBACK(on_draw_event), NULL); 
  g_signal_connect(window, "destroy", G_CALLBACK(gtk_main_quit), NULL);  
    
  g_signal_connect(window, "button-press-event", G_CALLBACK(clicked), NULL);
 
  gtk_window_set_position(GTK_WINDOW(window), GTK_WIN_POS_CENTER);
  gtk_window_set_default_size(GTK_WINDOW(window), 1920, 1080); 
  gtk_window_set_title(GTK_WINDOW(window), "Cars");

  gtk_widget_show_all(window);

  g_timeout_add (500, car_movement, NULL);


  gtk_main();

  return 0;
}

















/*


  for(int j=0; j<100; j++)
  {
	printf("%d ", lane1[j]);
  }
  printf("\n");

  printf("\n");

  printf("\n");

  for(int j=0; j<100; j++)
  {
	printf("%d ", lane2[j]);
  }
  printf("\n");

  printf("\n");

  printf("\n");

  for(int j=0; j<100; j++)
  {
	printf("%d ", lane3[j]);
  }

  printf("\n");

  printf("\n");
*/
