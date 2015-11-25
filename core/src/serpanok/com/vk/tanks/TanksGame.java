package serpanok.com.vk.tanks;

import java.util.List;

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
	SpriteBatch batch;
	Texture texture;
	List<String> map;
	public final int SIZE=30;
	
	TextureRegion T_extures[];
	TextureRegion T_tanks[][][];
	TextureRegion T_base;
	TextureRegion T_explosion;
	
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
        String mapFileName = "bin/map1.txt";
        try
        {
        	map = Files.readAllLines(Paths.get(mapFileName)); 
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
		
    	int y = 0;
    	for (String line : map) {
    		for(int x=0; x<11; x++)
    		{
    			char element = line.charAt(x);
        		TextureRegion smallElenemt = null;
        		TextureRegion bigElenemt = null;
        		
        		if( element == '#' )
        		{
        			smallElenemt = T_extures[1];
        		}
        		else if( element == '*' )
        		{
        			smallElenemt = T_extures[0];
        		}
        		else if( element == 'G' )
        		{
        			smallElenemt = T_extures[2];
        		}
        		else if( element == 'I' )
        		{
        			smallElenemt = T_extures[3];
        		}
        		else if( element == 'W' )
        		{
        			smallElenemt = T_extures[4];
        		}
        		else if( element == 'B' )
        		{
        			bigElenemt = T_base;
        		}
        		else if( element == 'T' )
        		{
        			bigElenemt = T_tanks[1][3][0];
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
    		y++;
        }
		
		batch.end();
		
	}
}
