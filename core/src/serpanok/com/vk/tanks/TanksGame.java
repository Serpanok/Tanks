package serpanok.com.vk.tanks;

import java.util.List;
import java.util.ArrayList;

//графика
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//чтение из файла
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TanksGame extends ApplicationAdapter {
	public SpriteBatch batch;
	public Texture texture;
	public final int SIZE=30;
	
	public TanksObject map[][] = new TanksObject[11][11];
	public List<TanksTank> tanks = new ArrayList<TanksTank>();
	
	public TextureRegion T_extures[];
	public TextureRegion T_tanks[][][];
	public TextureRegion T_base;
	public TextureRegion T_explosion;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		texture = new Texture(Gdx.files.internal("texture_1.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        //создание текстур
        T_extures = new TextureRegion[6];
        T_extures[0] = new TextureRegion(texture, 0, 0, SIZE, SIZE);
        T_extures[1] = new TextureRegion(texture, SIZE, 0, SIZE, SIZE);
        T_extures[2] = new TextureRegion(texture, SIZE*2, 0, SIZE, SIZE);
        T_extures[3] = new TextureRegion(texture, SIZE*3, 0, SIZE, SIZE);
        T_extures[4] = new TextureRegion(texture, 0, SIZE, SIZE, SIZE);
        T_extures[5] = new TextureRegion(texture, SIZE, SIZE, SIZE, SIZE);
        T_base = new TextureRegion(texture, SIZE*8, 0, SIZE*2, SIZE*2);
        T_explosion = new TextureRegion(texture, SIZE*4, 0, SIZE*2, SIZE*2);
        
        //заполнение текстур танков
        T_tanks = new TextureRegion[2][4][4];
        for(int type=0; type<2; type++)
        {
        	for(int level=0; level<4; level++)
            {
	        	for(int direction=0; direction<4; direction++)
	            {
	        		int x = (type * SIZE * 8) + (direction * SIZE * 2);
	        		int y = (SIZE * 2) + (level * SIZE * 2);
	        		
	        		T_tanks[type][level][direction] = new TextureRegion(texture, x, y, SIZE*2, SIZE*2);
	            }
            }
        }
        
        //чтение карты
        List<String> file;
        String mapFileName = "bin/map1.txt";
        try
        {
        	file = Files.readAllLines(Paths.get(mapFileName));
        	
            //инициализация карты
            int y = 0;
            for (String line : file) {
        		for(int x=0; x<11; x++)
        		{
        			char element		= line.charAt(x);
        			int componentType	= 0;
        			int textureType		= -1;
        			
        			if( element == '*' )
            		{
            			componentType = 3;
        				textureType = 0;
            		}
            		else if( element == '#' )
            		{
        				componentType = 3;
        				textureType = 1;
            		}
            		else if( element == 'G' )
            		{
            			componentType = 1;
        				textureType = 2;
            		}
            		else if( element == 'W' )
            		{
            			componentType = 2;
        				textureType = 5;
            		}
            		else if( element == 'B' )
            		{
        				System.out.println("TanksBase");
            			componentType = 4;
            			map[y][x] = new TanksBase();
            		}
            		else if( element == 'T' )
            		{
        				System.out.println("TanksTank");
            			System.out.println("tank/x=" + (x * SIZE * 2) + "/y=" +  ( y* SIZE * 2));
        				System.out.println();
        				
        				map[y][x] = new TanksObject();
        				
            			tanks.add( new TanksTank( 0, 0, false, 0, x * SIZE * 2, y * SIZE * 2 ) );
            			
            			continue;
            		}
            		else
            		{
            			map[y][x] = new TanksObject();
            		}
        			
        			if( textureType != -1 )
        			{
        				System.out.println("TanksTexture");
        				map[y][x] = new TanksTexture( textureType );
        			}

        			System.out.println(element + " - " + componentType + "/x=" + x + "/y=" + y);
        			
        			//map[y][x] = new TanksTexture(textureType);
        			map[y][x].componentType = componentType;
        			map[y][x].x = x;
        			map[y][x].y = y;
    				System.out.println();
        		}
        		y++;
            }
        }
        catch(IOException e)
        {
        	e.printStackTrace();
        }
	}

	@Override
	public void render () {
		//System.out.println(i);
		Gdx.gl.glClearColor(1, 0, 0, 1);
		
		batch.begin();
		
		//отрисовка танков
		for(int i=0; i < tanks.size(); i++)
		{
			TanksTank tempTank = tanks.get(i);
			
			System.out.println("tankBuild" + "[" + tempTank.type + "]" + "[" + tempTank.level + "]" + "[" + tempTank.direction + "]" + "/x=" + tempTank.x + "/y=" + tempTank.y);
			
			batch.draw(T_tanks[ tempTank.type ][ tempTank.level ][ tempTank.direction ], tempTank.x, tempTank.y);
		}
		
		//отрисовка карты
    	for (int y=0; y<11; y++) {
    		for(int x=0; x<11; x++)
    		{
    			System.out.println("build/x=" + x + "/y=" + y);
    			TextureRegion smallElenemt = null;
        		TextureRegion bigElenemt = null;
        		
        		//трава
        		if( map[y][x].componentType == 1 )
        		{
        			smallElenemt = T_extures[ 2 ];
        		}
        		//вода
        		if( map[y][x].componentType == 2 )
        		{
        			smallElenemt = T_extures[ 4 ];
        		}
        		//стена
        		else if( map[y][x].componentType == 3 )
        		{
        			TanksTexture tempWall = (TanksTexture) map[y][x];
        			smallElenemt = T_extures[ tempWall.type ];
        		}
        		//база
        		else if( map[y][x].componentType == 4 )
        		{
        			bigElenemt = T_base;
        		}
        		else if( map[y][x].componentType == 6 )
        		{
        			TanksTank tempTank = (TanksTank) map[y][x];
        			bigElenemt = T_tanks[ tempTank.type ][ tempTank.level ][ tempTank.direction ];
        		}
        		
        		if(smallElenemt != null)
        		{
        			batch.draw(smallElenemt, x*SIZE*2,		y*SIZE*2);
        			batch.draw(smallElenemt, x*SIZE*2+SIZE,	y*SIZE*2);
        			batch.draw(smallElenemt, x*SIZE*2,		y*SIZE*2+SIZE);
        			batch.draw(smallElenemt, x*SIZE*2+SIZE,	y*SIZE*2+SIZE);
        		}
        		else if(bigElenemt != null)
        		{
        			batch.draw(bigElenemt, x*SIZE*2, y*SIZE*2);
        		}
    		}
        }
		
		batch.end();
		
	}
}
