package serpanok.com.vk.tanks;

public class TanksBonus extends TanksObject {
	
	public int type;				//���
	public TanksObject tank;		//��� ��� ����������� �����
	public int deadline;			//����� ��������
	
	public int useStatus = 0;		//������ ������������� (0 - �� ��������; 1 - �������� � �������; 2 - �������� � �� �������)
	
	TanksBonus( int type_, TanksGame TanksGame_ )
	{
		this.type = type_;
		this.TanksGame = TanksGame_;
		this.deadline = this.TanksGame.frameI + this.TanksGame.BONUS_LIVE;
		
		this.x = this.TanksGame.randomizer.rand.nextInt( this.TanksGame.AREA_SIZE - 2 ) + 1;
		this.y = this.TanksGame.randomizer.rand.nextInt( this.TanksGame.AREA_SIZE - 2 ) + 1;
	}
	
	TanksBonus( TanksGame TanksGame_ )
	{
		this.TanksGame = TanksGame_;
		this.type = this.TanksGame.randomizer.rand.nextInt( 2 );
		this.deadline = this.TanksGame.frameI + this.TanksGame.BONUS_LIVE;
		
		this.x = this.TanksGame.randomizer.rand.nextInt( this.TanksGame.AREA_SIZE - 2 ) + 1;
		this.y = this.TanksGame.randomizer.rand.nextInt( this.TanksGame.AREA_SIZE - 2 ) + 1;
	}
}
