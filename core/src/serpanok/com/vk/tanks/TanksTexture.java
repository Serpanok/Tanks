package serpanok.com.vk.tanks;

/*import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;*/

public class TanksTexture extends TanksObject {
	
	/* 0 - ������,
	 * 1 - �����
	 * 2 - ����
	 * 3 - ��
	 * 4 - ����(1)
	 * 5 - ����(2) */
	public int type;			//���
	public int strength	= -1;	//���������� ���������
	
	TanksTexture( int type_ )
	{
		this.type = type_;
		
		if(type_ == 0)
		{
			this.strength = 3;
		}
	}
}