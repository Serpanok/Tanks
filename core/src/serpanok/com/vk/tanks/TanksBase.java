package serpanok.com.vk.tanks;

/*import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;*/

public class TanksBase extends TanksObject {
	
	public int strength;	//���������� ���������
	
	public boolean hit( TanksObject tank_ )
	{
		System.out.println("HIT BASE!!!!!!!!" + x + "/" + y);
		
		strength--;
			
		//���� ��������� ����� �� 0 �� ���������� ������ � �����
		if(strength <= 0)
		{
			this.TanksGame.isGameActive = false;
			return true;
		}
		
		return false;
	}
	
	TanksBase( TanksGame TanksGame_ )
	{
		this.TanksGame = TanksGame_;
	}
	
}
