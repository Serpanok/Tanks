package serpanok.com.vk.tanks;

import java.util.List;

//import org.lwjgl.input.Keyboard;

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

//рандомайзер
import java.util.Random;

public class TanksGame extends ApplicationAdapter {
	public SpriteBatch batch;
	public Texture texture;
	
	public boolean isGameActive = true;
	
	public final int BLOCK_SIZE	= 30;
	public final int AREA_SIZE	= 15;
	public final int SHELL_SPEED= 10;
	public final int TANK_SPEED	= 15;
	public final int SHOT_SPEED	= 60;
	public final int BANG_SPEED	= 15;
	
	public final int BONUS_RANGE= 2000;
	public final int BONUS_LIVE	= 800;
	
	Random rand = new Random();
	
	public TanksKeyChecker keyChecker;
	
	public TanksObject 		map[][]	= new TanksObject[AREA_SIZE][AREA_SIZE];
	public List<TanksTank>	tanks	= new ArrayList<TanksTank>();
	public List<TanksShell> shells	= new ArrayList<TanksShell>();
	public List<TanksObject>bangs	= new ArrayList<TanksObject>();
	public List<TanksBonus>	bonuses	= new ArrayList<TanksBonus>();
	
	public TextureRegion T_textures[];	//текстуры
	public TextureRegion T_texturesRuins[];	//разрушение текстур
	public TextureRegion T_bonuses[];	//бонусы
	public TextureRegion T_tanks[][][];	//танки
	public TextureRegion T_bang;		//взрыв
	public TextureRegion T_base;		//база игрока
	public TextureRegion T_explosion;
	public TextureRegion T_shells[];	//выстрелы
	public TextureRegion T_gameOver;	//Конец игры
	
	public int frameI=0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		texture = new Texture(Gdx.files.internal("texture_1.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        //создание текстур
        T_textures	 		= new TextureRegion[6];
        T_textures[0]		= new TextureRegion(texture, 0, 0, BLOCK_SIZE, BLOCK_SIZE);
        T_textures[1]		= new TextureRegion(texture, BLOCK_SIZE, 0, BLOCK_SIZE, BLOCK_SIZE);
        T_textures[2]		= new TextureRegion(texture, BLOCK_SIZE*2, 0, BLOCK_SIZE, BLOCK_SIZE);
        T_textures[3]		= new TextureRegion(texture, BLOCK_SIZE*3, 0, BLOCK_SIZE, BLOCK_SIZE);
        T_textures[4]		= new TextureRegion(texture, 0, BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        T_textures[5]		= new TextureRegion(texture, BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        T_texturesRuins	 	= new TextureRegion[2];
        T_texturesRuins[0]	= new TextureRegion(texture, 360, 540,  BLOCK_SIZE*2, BLOCK_SIZE*2);
        T_texturesRuins[1]	= new TextureRegion(texture, 420, 540,  BLOCK_SIZE*2, BLOCK_SIZE*2);
        T_bonuses	 		= new TextureRegion[6];
        for(int i=0;i<6;i++)
        {
        	T_bonuses[i]	= new TextureRegion(texture, (BLOCK_SIZE*2)*i, 540,  BLOCK_SIZE*2, BLOCK_SIZE*2);
        }
        T_base				= new TextureRegion(texture, BLOCK_SIZE*8, 0, BLOCK_SIZE*2, BLOCK_SIZE*2);
        T_explosion			= new TextureRegion(texture, BLOCK_SIZE*4, 0, BLOCK_SIZE*2, BLOCK_SIZE*2);
        T_shells			= new TextureRegion[4];
        T_shells[0]			= new TextureRegion(texture, 186, 6,  16, 16);
        T_shells[1]			= new TextureRegion(texture, 218, 38, 16, 16);
        T_shells[2]			= new TextureRegion(texture, 186, 38, 16, 16);
        T_shells[3]			= new TextureRegion(texture, 218, 6,  16, 16);
        T_bang				= new TextureRegion(texture, 120, 0,  BLOCK_SIZE*2, BLOCK_SIZE*2);
        T_gameOver			= new TextureRegion(texture, 0, 300,  480, 240);
        
        //заполнение текстур танков
        T_tanks = new TextureRegion[2][4][4];
        for(int type=0; type<2; type++)
        {
        	for(int level=0; level<4; level++)
            {
	        	for(int direction=0; direction<4; direction++)
	            {
	        		int x = (type * BLOCK_SIZE * 8) + (direction * BLOCK_SIZE * 2);
	        		int y = (BLOCK_SIZE * 2) + (level * BLOCK_SIZE * 2);
	        		
	        		T_tanks[type][level][direction] = new TextureRegion(texture, x, y, BLOCK_SIZE*2, BLOCK_SIZE*2);
	            }
            }
        }
        
        //чтение карты
        List<String> file;
        String mapFileName = "bin/map_1.txt";
        try
        {
        	file = Files.readAllLines(Paths.get(mapFileName));
        	
            //инициализация карты
            int y = 0;
            for (String line : file) {
        		for(int x=0; x < AREA_SIZE; x++)
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
            			map[y][x] = new TanksBase( this );
            		}
            		else if( element == 'T' )
            		{
        				System.out.println("TanksTank");
            			System.out.println("tank/x=" + x + "/y=" +  y);
        				System.out.println();
        				
        				map[y][x] = new TanksObject();
        				
            			tanks.add( new TanksTank( 0, 0, false, 0, x, y, this) );
            			
            			continue;
            		}
            		else
            		{
            			map[y][x] = new TanksObject();
            		}
        			
        			if( textureType != -1 )
        			{
        				System.out.println("TanksTexture");
        				map[y][x] = new TanksTexture( textureType, this );
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
		frameI++;
		
		if(isGameActive)
		{
			//рандомные события
			//System.out.println(rand.nextFloat());
			//System.out.println(rand.nextInt(100));
			
			
			//выдача бонусов
			if( this.frameI % this.BONUS_RANGE == 0 )
			{
				this.bonuses.add( new TanksBonus( this ) );
			}
			
			
			//отлов нажатия клавиш
			int moveDirection = -1;
			if( this.keyChecker.isTop() )
			{
				moveDirection = 0;
			}
			else if( this.keyChecker.isRight() )
			{
				moveDirection = 1;
			}
			else if( this.keyChecker.isBack() )
			{
				moveDirection = 2;
			}
			else if( this.keyChecker.isLeft() )
			{
				moveDirection = 3;
			}
			if(moveDirection > -1)
			{
				tanks.get(0).move(moveDirection);
			}
			if( this.keyChecker.isSpace() )
			{
				tanks.get(0).shot();
			}

			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			/*if(frameI%30==0)
			{
				int direction = 0;
				if(frameI == 630)
				{
					tanks.get(0).shot();
					direction = 2;
				}
				else if(frameI == 450)
				{
					direction = 2;
				}
				else if(frameI == 420)
				{
					tanks.get(0).shot();
					direction = 2;
				}
				else if(frameI >= 360)
				{
					direction = 1;
				}
				else if(frameI >= 90)
				{
					direction = 3;
				}
				
				tanks.get(0).move(direction);
			}*/
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
			//полёт пули
			if(frameI%SHELL_SPEED == 0)
			{
				for(int i=0; i < shells.size(); i++)
				{
					if( shells.get(i).isActive )
					{
						shells.get(i).move();
					}
				}
			}
		}
		
		//очистка экрана
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//System.out.println(i);
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		
		batch.begin();
		
		//Keyboard.isKeyDown(Keyboard.KEY_LEFT)
        
		/*try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//System.out.println(i1);
		
		//отрисовка танков
		for(int i=0; i < this.tanks.size(); i++)
		{
			TanksTank tempTank = this.tanks.get(i);
			
			if(tempTank.isActive)
			{
				//System.out.println("tankBuild" + "[" + tempTank.type + "]" + "[" + tempTank.level + "]" + "[" + tempTank.direction + "]" + "/x=" + tempTank.x + "/y=" + tempTank.y);
				batch.draw(T_tanks[ tempTank.type ][ tempTank.level ][ tempTank.direction ], tempTank.x*BLOCK_SIZE*2, tempTank.y*BLOCK_SIZE*2);
			}
		}
		
		//отрисовка взрывов
		for(int i=0; i < this.bangs.size(); i++)
		{
			TanksObject tempBang = this.bangs.get(i);
			
			if(tempBang.isActive)
			{
				//System.out.println("tankBuild" + "[" + tempTank.type + "]" + "[" + tempTank.level + "]" + "[" + tempTank.direction + "]" + "/x=" + tempTank.x + "/y=" + tempTank.y);
				batch.draw(T_bang, tempBang.x*BLOCK_SIZE*2, tempBang.y*BLOCK_SIZE*2);
				
				if(tempBang.createTime < this.frameI - this.BANG_SPEED )
				{
					this.bangs.get(i).isActive = false;
				}
			}
		}
		
		//отрисовка карты
    	for (int y=0; y < AREA_SIZE; y++) {
    		for(int x=0; x < AREA_SIZE; x++)
    		{
    			//System.out.println("build/x=" + x + "/y=" + y);
    			TextureRegion smallElenemt	= null;
        		TextureRegion bigElenemt	= null;
        		TextureRegion ruin		= null;
        		
        		//трава
        		if( map[y][x].componentType == 1 )
        		{
        			smallElenemt = T_textures[ 2 ];
        		}
        		//вода
        		if( map[y][x].componentType == 2 )
        		{
        			smallElenemt = T_textures[ 4 ];
        		}
        		//стена
        		else if( map[y][x].componentType == 3 )
        		{
        			TanksTexture tempWall = (TanksTexture) map[y][x];
        			smallElenemt = T_textures[ tempWall.type ];
        			
        			if( tempWall.type == 0 )
        			{
        				if(tempWall.strength == 1)
        				{
        					ruin = T_texturesRuins[0];
        				}
        			}
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
        			batch.draw(smallElenemt, x*BLOCK_SIZE*2,			y*BLOCK_SIZE*2);
        			batch.draw(smallElenemt, x*BLOCK_SIZE*2+BLOCK_SIZE,	y*BLOCK_SIZE*2);
        			batch.draw(smallElenemt, x*BLOCK_SIZE*2,			y*BLOCK_SIZE*2+BLOCK_SIZE);
        			batch.draw(smallElenemt, x*BLOCK_SIZE*2+BLOCK_SIZE,	y*BLOCK_SIZE*2+BLOCK_SIZE);
        		}
        		else if(bigElenemt != null)
        		{
        			batch.draw(bigElenemt, x*BLOCK_SIZE*2, y*BLOCK_SIZE*2);
        		}
        		
        		if(ruin != null)
        		{
        			batch.draw(ruin, x*BLOCK_SIZE*2, y*BLOCK_SIZE*2);
        		}
    		}
        }
    	
		//отрисовка активных бонусов
		for(int i=0; i < this.bonuses.size(); i++)
		{
			TanksBonus tempBonus = this.bonuses.get(i);
			if( tempBonus.isActive && tempBonus.tank == null )
			{
				batch.draw(this.T_bonuses[ tempBonus.type ], tempBonus.x*BLOCK_SIZE*2, tempBonus.y*BLOCK_SIZE*2);
				
				if(tempBonus.deadline < this.frameI)
				{
					this.bonuses.get(i).isActive = false;
				}
			}
		}
    	
		//отрисовка снарядов
		for(int i=0; i < this.shells.size(); i++)
		{
			TanksShell tempShell = this.shells.get(i);
			if( tempShell.isActive )
			{
				batch.draw(T_shells[ tempShell.direction ], tempShell.x*BLOCK_SIZE*2 + 22, tempShell.y*BLOCK_SIZE*2 + 22);
			}
		}
		
		if(!isGameActive)
		{
			batch.draw(T_gameOver, AREA_SIZE * BLOCK_SIZE - 240, AREA_SIZE * BLOCK_SIZE - 150);
		}
		
		batch.end();
		
	}
	
	public TanksGame( TanksKeyChecker keyChecker_ )
	{
		keyChecker = keyChecker_;
	}
}
