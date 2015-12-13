package serpanok.com.vk.tanks;

public class TanksBonus extends TanksObject {
	
	public int type;				//���
	public TanksObject tank;		//��� ��� ����������� �����
	public int deadline;			//����� ��������
	
	TanksBonus( int type_, TanksGame TanksGame_ )
	{
		this.type = type_;
		this.TanksGame = TanksGame_;
		this.deadline = this.TanksGame.frameI + this.TanksGame.BONUS_LIVE;
		
		this.x = this.TanksGame.rand.nextInt( this.TanksGame.AREA_SIZE - 2 ) + 1;
		this.y = this.TanksGame.rand.nextInt( this.TanksGame.AREA_SIZE - 2 ) + 1;
	}
	
	TanksBonus( TanksGame TanksGame_ )
	{
		this.TanksGame = TanksGame_;
		this.type = this.TanksGame.rand.nextInt( 6 );
		this.deadline = this.TanksGame.frameI + this.TanksGame.BONUS_LIVE;
		
		this.x = this.TanksGame.rand.nextInt( this.TanksGame.AREA_SIZE - 2 ) + 1;
		this.y = this.TanksGame.rand.nextInt( this.TanksGame.AREA_SIZE - 2 ) + 1;
	}
}
