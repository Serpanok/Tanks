package serpanok.com.vk.tanks;

import java.util.List;

//import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

//�������
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//������ �� �����
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TanksGame extends ApplicationAdapter {
	public SpriteBatch batch;
	public Texture texture;
	
	public boolean isGameActive = true;
	
	
	public final int BLOCK_SIZE	= 30;
	public final int AREA_SIZE	= 15;
	
	public final int SHELL_SPEED	= 5;
	public final int TANK_SPEED[]	= { 25, 20, 15, 10};
	public final int SHOT_SPEED[]	= { 60, 45, 30, 15 };
	public final int BANG_SPEED		= 15;
	
	public final int BONUS_RANGE= 2000;
	public final int BONUS_LIVE	= 800;
	
	public final int SPAWN_FREQUENCY		= 500;
	public final double SPAWN_COEFFICIENT	= 0.3;
	
	public final double BOT_CHANGE_DIRECTION		= 0.3;	//����������� ����� ����������� ������ ��������
	public final double BOT_SHOT_COEFFICIENT		= 0.2;	//����������� ��������
	public final double BOT_SHOT_PLAYER_COEFFICIENT = 0.6;	//����������� �������� ��� ��������� ����������
	
	
	public TanksKeyChecker keyChecker;
	public TanksRandomizer randomizer = new TanksRandomizer();
	
	public TanksObject 		map[][]	= new TanksObject[AREA_SIZE][AREA_SIZE];
	public List<TanksSpawn>	spawns	= new ArrayList<TanksSpawn>();
	public List<TanksTank>	tanks	= new ArrayList<TanksTank>();
	public List<TanksShell> shells	= new ArrayList<TanksShell>();
	public List<TanksObject>bangs	= new ArrayList<TanksObject>();
	public List<TanksBonus>	bonuses	= new ArrayList<TanksBonus>();
	
	public TextureRegion T_textures[];	//��������
	public TextureRegion T_texturesRuins[];	//���������� �������
	public TextureRegion T_bonuses[];	//������
	public TextureRegion T_tanks[][][];	//�����
	public TextureRegion T_bang;		//�����
	public TextureRegion T_base;		//���� ������
	public TextureRegion T_explosion;
	public TextureRegion T_shells[];	//��������
	public TextureRegion T_gameOver;	//����� ����
	
	public int frameI=0;
	public int botsLeft;	//���������� ���������� �����
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		texture = new Texture(Gdx.files.internal("texture_1.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        //�������� �������
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
        T_bonuses	 		= new TextureRegion[2];
        for(int i=0;i<2;i++)
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
        
        //���������� ������� ������
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
        
        //������ �����
        List<String> file;
        String mapFileName = "bin/map_1.txt";
        try
        {
        	file = Files.readAllLines(Paths.get(mapFileName));
        	
            //������������� �����
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
            		else if( element == 'S' )
            		{
        				System.out.println("TanksSpawn");
            			System.out.println("spawn/x=" + x + "/y=" +  y);
        				System.out.println();
        				
        				map[y][x] = new TanksObject();
        				
        				spawns.add( new TanksSpawn( x, y, this ) );
            			
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
			//������ �������
			if( this.frameI % this.BONUS_RANGE == 0 )
			{
				this.bonuses.add( new TanksBonus( this ) );
			}
			
			//������ �������
			if( this.frameI % this.SPAWN_FREQUENCY == 0 || this.frameI == 50 )
			{
				System.out.println("SpawnTime");
				for(int i = 0; i < this.spawns.size(); i++)
				{
					this.spawns.get(i).create( this.frameI == 50 );
				}
			}
			
			//����� ������� ������
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
			
			//�����-����� ����� �������� ����� ��� ��������
			if( this.frameI % 5 == 0 && this.frameI > 50 )
			{
				for(int i = 0; i < this.tanks.size(); i++)
				{
					TanksTank tempTank = this.tanks.get(i);
					
					if( tempTank.isActive && tempTank.isBot && tempTank.isCanMove() )
					{
						int directions[] = { 0,0,0,0 };
						int directionsCount = 0;
						boolean isCanMoveToCurrentDirection = false;
						
						//����������� �������� �����
						if( this.map[ tempTank.y + 1 ][ tempTank.x ].componentType <= 1 )
						{
							directions[directionsCount++] = 0;
							
							if( tempTank.direction == 0 )
							{
								isCanMoveToCurrentDirection = true;
							}
						}
						//����������� �������� ������
						if( this.map[ tempTank.y ][ tempTank.x + 1 ].componentType <= 1 )
						{
							directions[directionsCount++] = 1;
							
							if( tempTank.direction == 1 )
							{
								isCanMoveToCurrentDirection = true;
							}
						}
						//����������� �������� ����
						if( this.map[ tempTank.y - 1 ][ tempTank.x ].componentType <= 1 )
						{
							directions[directionsCount++] = 2;
							
							if( tempTank.direction == 2 )
							{
								isCanMoveToCurrentDirection = true;
							}
						}
						//����������� �������� �����
						if( this.map[ tempTank.y ][ tempTank.x - 1 ].componentType <= 1 )
						{
							directions[directionsCount++] = 3;
							
							if( tempTank.direction == 3 )
							{
								isCanMoveToCurrentDirection = true;
							}
						}
						
						//System.out.println("Tank at " + tempTank.x + "/" + tempTank.y + " can move to " + directionsCount + " directions");
						
						int moveTo = directions[this.randomizer.rand.nextInt( directionsCount ) ];
						
						if( isCanMoveToCurrentDirection && !this.randomizer.isEvent( this.BOT_CHANGE_DIRECTION ) )
						{
							moveTo = tempTank.direction;
						}
						
						//System.out.println("Tank move to " + moveTo );
						
						tempTank.move(moveTo);
					}
				}
			}
			
			//�����-����� ����� �������� ����� ��� ���������
			if( this.frameI % 15 == 0 )
			{
				TanksTank playerTank = this.tanks.get(0);
				
				System.out.println("BOT shot time");
				for(int i = 0; i < this.tanks.size(); i++)
				{
					TanksTank tempTank = this.tanks.get(i);
					
					if( tempTank.isActive && tempTank.isBot )
					{
						boolean isSeePlayer = false;
						
						//����������� ������� ������ �� ����� ��� �� �����������
						if( playerTank.y == tempTank.y )
						{
							if(	( tempTank.direction == 1 && playerTank.x < tempTank.x )
								||
								( tempTank.direction == 3 && playerTank.x > tempTank.x )
							)
							{
								isSeePlayer = true;
							}
						}
						//����������� ������� ������ �� ����� ��� �� ���������
						else if( playerTank.x == tempTank.x )
						{
							if(	( tempTank.direction == 0 && playerTank.y > tempTank.y )
								||
								( tempTank.direction == 2 && playerTank.y < tempTank.y )
							)
							{
								isSeePlayer = true;
							}
						}
						
						if( isSeePlayer )
						{
							System.out.println("SEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE PLAYER!!!!!!!!!!!!!" );
						}
						
						//���� �������� � �������
						if(	(!isSeePlayer && this.randomizer.isEvent( this.BOT_SHOT_COEFFICIENT))
							||
							(isSeePlayer && this.randomizer.isEvent( this.BOT_SHOT_PLAYER_COEFFICIENT))
						)
						{
							tempTank.shot();
						}
					}
				}
			}
			
			//���� ����
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
		
		//������� ������
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		
		//��������� ������
		for(int i=0; i < this.tanks.size(); i++)
		{
			TanksTank tempTank = this.tanks.get(i);
			
			if(tempTank.isActive)
			{
				//System.out.println("tankBuild" + "[" + tempTank.type + "]" + "[" + tempTank.level + "]" + "[" + tempTank.direction + "]" + "/x=" + tempTank.x + "/y=" + tempTank.y);
				batch.draw(T_tanks[ tempTank.type ][ tempTank.level ][ tempTank.direction ], tempTank.x*BLOCK_SIZE*2, tempTank.y*BLOCK_SIZE*2);
			}
		}
		
		//��������� �������
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
		
		//��������� �����
    	for (int y=0; y < AREA_SIZE; y++) {
    		for(int x=0; x < AREA_SIZE; x++)
    		{
    			TextureRegion smallElenemt	= null;
        		TextureRegion bigElenemt	= null;
        		TextureRegion ruin			= null;
        		
        		//�����
        		if( map[y][x].componentType == 1 )
        		{
        			smallElenemt = T_textures[ 2 ];
        		}
        		//����
        		if( map[y][x].componentType == 2 )
        		{
        			smallElenemt = T_textures[ 4 ];
        		}
        		//�����
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
        		//����
        		else if( map[y][x].componentType == 4 )
        		{
        			bigElenemt = T_base;
        		}
        		//����
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
    	
		//��������� �������� �������
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
    	
		//��������� ��������
		for(int i=0; i < this.shells.size(); i++)
		{
			TanksShell tempShell = this.shells.get(i);
			if( tempShell.isActive )
			{
				batch.draw(T_shells[ tempShell.direction ], tempShell.x*BLOCK_SIZE*2 + 22, tempShell.y*BLOCK_SIZE*2 + 22);
			}
		}
		
		//���� ���� ����������
		if(!isGameActive)
		{
			batch.draw(T_gameOver, AREA_SIZE * BLOCK_SIZE - 240, AREA_SIZE * BLOCK_SIZE - 150);
		}
		
		batch.end();
		
	}
	
	public TanksGame( TanksKeyChecker keyChecker_ )
	{
		keyChecker = keyChecker_;

		botsLeft = 20;
	}
}
