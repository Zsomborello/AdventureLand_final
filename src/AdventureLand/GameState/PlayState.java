// The main playing GameState.
// Contains everything you need for gameplay:
// Player, TileMap, Diamonds, etc.
// Updates and draws all game objects.

package AdventureLand.GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import AdventureLand.Entity.*;
import AdventureLand.Manager.*;
import AdventureLand.HUD.Hud;
import AdventureLand.Main.GameLoop;
import AdventureLand.TileMap.TileMap;


public class PlayState extends GameState {
	
	// player
	private Player player;

	private ArrayList<Enemy> enemies;
	private ArrayList<Boss> boss;
	private Switch sw, sw2;
	private Teleport tp1, tp2;
	private ArrayList<Trap> traps;
 

	
	// tilemap
	private TileMap tileMap;
	
	// goldcoins
	private ArrayList<GoldCoin> goldcoins;
	
	//silvercoins
	private ArrayList<SilverCoin> silvercoins;
	
	//potions
	private ArrayList<VPotion> vpotions;
	private ArrayList<HPotion> hpotions;
	
	// items
	private ArrayList<Item> items;
	
	// sparkles
	private ArrayList<Sparkle> sparkles;
	
	// camera position
	private int xsector;
	private int ysector;
	private int sectorSize; 
	
	// hud
	private Hud hud;
	
	// events
	private boolean blockInput;
	private boolean eventStart;
	private boolean eventFinish;
	private int eventTick, musicloop, cnt;

	private AudioPlayer bgmusic, collect, tilechange, gunshot, teleport;
	
	
	public static String player1Name;
	
	// transition box
	private ArrayList<Rectangle> boxes;
	
	public PlayState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init() {
		
		//GameName
		player1Name = JOptionPane.showInputDialog("Enter your name:");
		
		// create lists
		goldcoins = new ArrayList<GoldCoin>();
		silvercoins = new ArrayList<SilverCoin>();
		vpotions = new ArrayList<VPotion>();
		hpotions = new ArrayList<HPotion>();
		sparkles = new ArrayList<Sparkle>();
		items = new ArrayList<Item>();
		traps = new ArrayList<Trap>();
		
		cnt = 0;
		musicloop = 0;
		
		// load map
		tileMap = new TileMap(16);
		tileMap.loadTiles("/Tilesets/tilesets_new.png");
		tileMap.loadMap("/Maps/Terkep.map");
		
		// create entities
		player = new Player(tileMap);
        enemies = new ArrayList<Enemy>();
        boss = new ArrayList<Boss>();
        
        //create additional items
        sw = new Switch(tileMap);
		sw2 = new Switch(tileMap);
		
		tp1 = new Teleport(tileMap);
		tp2 = new Teleport(tileMap);

		
		// fill lists
		populateGoldCoins();
		populateSilverCoins();
		populateItems();
		populateEnemies();
		
		// initialize player
		player.setTilePosition(1, 1);
		player.setTotalGoldCoins(goldcoins.size());
		
		sw.setTilePosition(3, 38);
		sw2.setTilePosition(3, 4);
		
		tp1.setTilePosition(20, 1); 
		tp2.setTilePosition(20, 41);
		
		// set up camera position
		sectorSize = GameLoop.WIDTH;
		xsector = player.getx() / sectorSize;
		ysector = player.gety() / sectorSize;
		tileMap.setPositionImmediately(-xsector * sectorSize, -ysector * sectorSize);
		
		// load HUD
		hud = new Hud(player, goldcoins);
		
		//load background music
		bgmusic = new AudioPlayer("/Music/bgmusic.mp3");
		bgmusic.play();
			
		//load SFX
		gunshot = new AudioPlayer("/SFX/gunshot.wav");
		tilechange = new AudioPlayer("/SFX/tilechange.wav");
		collect = new AudioPlayer("/SFX/collectitem.wav");
	
		// start event
		boxes = new ArrayList<Rectangle>();
		eventStart = true;
		eventStart();
			
	}
	
	
	
	private void populateGoldCoins() {
		
		GoldCoin gc;
		
		 //1
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(1, 37);
		goldcoins.add(gc);
		
		//2
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(12, 36);
		goldcoins.add(gc);
		
		//3
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(5, 37);
		goldcoins.add(gc);
		
		//5
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(20, 38);
		goldcoins.add(gc);

		//6
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(5, 4);
		goldcoins.add(gc);
		
		//7
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(4, 1);
		goldcoins.add(gc);
		
		//8
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(6, 13);
		goldcoins.add(gc);
		
		//9
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(7, 13);
		goldcoins.add(gc);
		
		//10
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(3, 8);
		goldcoins.add(gc);
		
		//11
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(5, 8);
		goldcoins.add(gc);
		
		//12
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(7, 8);
		goldcoins.add(gc);
		
		//13
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(9, 8);
		goldcoins.add(gc);
		
		//14
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(11, 8);
		goldcoins.add(gc);
		
		//15
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(3, 11);
		goldcoins.add(gc);
		
		
		
		//level 2
		
		//1
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(3, 60);
		goldcoins.add(gc);
		
		//3
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(1, 42);
		goldcoins.add(gc);
		
		//4
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(5, 42);
		goldcoins.add(gc);
		
		//5
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(7, 42);
		goldcoins.add(gc);

		//6
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(9, 42);
		goldcoins.add(gc);
		
		//7
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(13, 41);
		goldcoins.add(gc);
		
		//8
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(15, 41);
		goldcoins.add(gc);
		
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(1, 77);
		goldcoins.add(gc);
		
		//9
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(17, 41);
		goldcoins.add(gc);
		
		//10
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(15, 78);
		goldcoins.add(gc);
		
		//13
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(3, 42);
		goldcoins.add(gc);
		
		//14
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(17, 78);
		goldcoins.add(gc);
		
		//15
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(13, 78);
		goldcoins.add(gc);
		
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(11, 78);
		goldcoins.add(gc);
		
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(3, 60);
		goldcoins.add(gc);
		
		gc = new GoldCoin(tileMap);
		gc.setTilePosition(4, 54);
		goldcoins.add(gc);
		
	}
	
private void populateSilverCoins() {
		
		SilverCoin sc;
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(5, 5);
		silvercoins.add(sc);
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(7, 7);
		silvercoins.add(sc);
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(7, 9);
		silvercoins.add(sc);
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(7, 11);
		silvercoins.add(sc);
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(13, 13);
		silvercoins.add(sc);
		
		//level 2
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(4, 60);
		silvercoins.add(sc);
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(5, 60);
		silvercoins.add(sc);
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(6, 60);
		silvercoins.add(sc);
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(7, 60);
		silvercoins.add(sc);
		
		sc = new SilverCoin(tileMap);
		sc.setTilePosition(8, 60);
		silvercoins.add(sc);
	
	}

	
private void populateEnemies() {
		
		Trap tr;
		Enemy en;
		Boss b;
	
	

		
		tr = new Trap(tileMap);
		tr.setPosition(504, 192);
		traps.add(tr);
		
		tr = new Trap(tileMap);
		tr.setPosition(264, 272);
		traps.add(tr);
	
		b = new Boss(tileMap);
		b.setTilePosition(5, 57);
		boss.add(b);
		
		en = new Enemy(tileMap);
		en.setTilePosition(3, 25);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(5, 23);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(7, 21);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(12, 25);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(13, 23);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(14, 21);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(12, 27);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(5, 29);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(5, 31);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(5, 33);
		enemies.add(en);
		
		
		en = new Enemy(tileMap);
		en.setTilePosition(9, 27);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(6, 1);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(12, 3);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(9, 3);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(6, 5);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(10, 7);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(6, 9);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(6, 11);
		enemies.add(en);
		
		//level2
		en = new Enemy(tileMap);
		en.setTilePosition(10, 49);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(10, 51);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(10, 53);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(10, 55);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(10, 57);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(10, 59);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(10, 61);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 42);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 44);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 46);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 48);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 50);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 65);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 67);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 69);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 71);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 73);
		enemies.add(en);
		
		en = new Enemy(tileMap);
		en.setTilePosition(18, 78);
		enemies.add(en);
		
}
	
	private void populateItems() {
		
		Item item;
		VPotion vpotion;
		HPotion hpotion;
				
		item = new Item(tileMap);
		item.setType(Item.BOAT);
		item.setTilePosition(3, 1);
		items.add(item);
		
		item = new Item(tileMap);
		item.setType(Item.WEAPON);
		item.setTilePosition(8,27);
		items.add(item);
		
		item = new Item(tileMap);
		item.setType(Item.KEY);
		item.setTilePosition(1,38);
		items.add(item);
		
		vpotion = new VPotion(tileMap);
		vpotion.setTilePosition(5, 38);
		vpotions.add(vpotion);
		
		hpotion = new HPotion(tileMap);
		hpotion.setTilePosition(20,37); 
		hpotions.add(hpotion);

		
	}
	
	public void update() {
	
		// check keys
		handleInput();

		// check events
		if(eventStart) eventStart();
		if(eventFinish || Player.life==0) eventFinish();
		
		cnt++;
		musicloop = cnt / 100;
		
		//looping the background music
		if (musicloop > 14 ) {
			bgmusic = new AudioPlayer("Music/bgmusic.mp3");
			bgmusic.play();
			cnt = 0;
		}
		
		ArrayList<Bullet> bullets = Player.getBullets();
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b = (Bullet) bullets.get(i);
			if (b.isVisible() == true) {
				b.update();
			} else {
				bullets.remove(i);
			}
		}
		
		
		if(player.numGoldCoins() == player.getTotalGoldCoins()) {
			eventFinish = blockInput = true;
		}
		
		
		
		//if the player collects 5 silver coins he gets 1 gold coin
		if(player.numSilverCoins()==5) {
			player.collectedGoldCoin();
			player.numSilverCoins = 0;
		}
		
		// update camera
		int oldxs = xsector;
		int oldys = ysector;
		xsector = player.getx() / sectorSize;
		ysector = player.gety() / sectorSize;
		tileMap.setPosition(-xsector * sectorSize, -ysector * sectorSize);
		tileMap.update();
		
		if(oldxs != xsector || oldys != ysector) {
			
		}
		
		if(tileMap.isMoving()) return;
		
		// update player
		player.update();
		
		
		
		if (player.hasKey()) {
			tileMap.setTile(11, 39, 1);
		}
		
		if (player.intersects(tp1)){
			teleport = new AudioPlayer("/SFX/teleport.wav");
			teleport.play();
			player.setTilePosition(1, 37);
			tileMap.setTile(1, 35, 1);
			tp1.update();
			}
		
		if (player.intersects(tp2)){
			teleport = new AudioPlayer("/SFX/teleport.wav");
			teleport.play();
			player.setTilePosition(1, 48);
			tp2.update();
			}
		
        //when the switch is turned on
		if (player.intersects(sw)){
			Switch.turned_on = true;
			tileMap.setTile(14, 15, 1);
			sw.update();
			if (sw.active == false) tilechange = new AudioPlayer("/SFX/tilechange.wav");
			tilechange.play();
		}
		
		if (player.intersects(sw2)){
			Switch.turned_on = true;
			tileMap.setTile(18, 5, 1);
			sw2.update();
			if (sw2.active == false) tilechange = new AudioPlayer("/SFX/tilechange.wav");
			tilechange.play();
		}
		
		for(int i = 0; i < enemies.size(); i++) {
			
			Enemy en = enemies.get(i);
			en.update();
			if (en.getRectangle().intersects(Bullet.getRectangle())) {
				enemies.remove(i);
				i--;
			}
			
			
			// player collects goldcoin
			if(player.intersects(en))  {
				enemies.remove(i);
				Player.life = Player.life-50;
				i--; }
		 }

		for(int i = 0; i < traps.size(); i++) {
			
			Trap tr = traps.get(i);
			tr.update();
			
			// player collects goldcoin
			if(player.intersects(tr)) {
				//TODO something
				}
		} 
		
		for(int i = 0; i < boss.size(); i++) {
			
			Boss b = boss.get(i);
			b.update();
			
			// player collects goldcoin
			if(player.intersects(b)) 
				if (player.hasWeapon()) {
				boss.remove(i);
				Player.life = 0;
				i--; 
				}
		} 
		
		
		// update goldcoins
	for(int i = 0; i < goldcoins.size(); i++) {
			
			GoldCoin gc = goldcoins.get(i);
			gc.update();
			
			// player collects goldcoin
			if(player.intersects(gc)) {
				
				// remove from list
				goldcoins.remove(i);
				i--;
				
				// increment amount of collected goldcoins
				player.collectedGoldCoin();
				
				// play collect sound
				collect = new AudioPlayer("/SFX/collectitem.wav");
				collect.play();
				
				// add new sparkle
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(gc.getx(), gc.gety());
				sparkles.add(s);
				
				
				
				
			}
		}
		
	// update silvercoins
	for(int i = 0; i < silvercoins.size(); i++) {
			
			SilverCoin sc = silvercoins.get(i);
			sc.update();
			
			// player collects silvercoin
			if(player.intersects(sc)) {
				
				// remove from list
				silvercoins.remove(i);
				i--;
				
				// increment amount of collected silvercoins
				player.collectedSilverCoin();
				
				// play collect sound
				collect = new AudioPlayer("/SFX/collectitem.wav");
				collect.play();
				
				// add new sparkle
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(sc.getx(), sc.gety());
				sparkles.add(s);
				
			}
		}
	
for(int i = 0; i < vpotions.size(); i++) {
			
			VPotion vp = vpotions.get(i);
			vp.update();
			
			// player collects vpotion
			if(player.intersects(vp)) {
				
				// remove from list
				vpotions.remove(i);
				i--;
				player.gotVPotion();
				
				// play collect sound
				collect = new AudioPlayer("/SFX/collectitem.wav");
				collect.play();
				
				// add new sparkle
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(vp.getx(), vp.gety());
				sparkles.add(s);	
			}
		}

		

      for(int i = 0; i < hpotions.size(); i++) {
	
      HPotion hp = hpotions.get(i);
      hp.update();
	
	  // player collects hpotion
      if(player.intersects(hp)) {
	    hpotions.remove(i);
	    i--;
	    player.gotHPotion();
	    
	    collect = new AudioPlayer("/SFX/collectitem.wav");
		collect.play();

        // add new sparkle
		Sparkle s = new Sparkle(tileMap);
		s.setPosition(hp.getx(), hp.gety());
		sparkles.add(s);	
       }
      }
		
		// update sparkles
		for(int i = 0; i < sparkles.size(); i++) {
			Sparkle s = sparkles.get(i);
			s.update();
			if(s.shouldRemove()) {
				sparkles.remove(i);
				i--;
			}
		}
		
		// update items
		for(int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			if(player.intersects(item)) {
				items.remove(i);
				i--;
				item.collected(player);
				collect = new AudioPlayer("/SFX/collectitem.wav");
				collect.play();
				Sparkle s = new Sparkle(tileMap);
				s.setPosition(item.getx(), item.gety());
				sparkles.add(s);
			}
		}
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw player
		player.draw(g);
		
		
		ArrayList bullets = Player.getBullets();
		for (int i = 0; i < bullets.size(); i++) {
			Bullet b= (Bullet) bullets.get(i);
			g.setColor(Color.GRAY);
			g.fillOval(b.getXb()-6, b.getYb(), 3, 3);
		}
		
		for(Boss b : boss) {
			b.draw(g);
		}
		
		for (Trap tr : traps) {
			tr.draw(g);
			}
		
		//draw enemies
		for(Enemy e : enemies) {
			e.draw(g);
		}
		
		// draw goldcoins
		for(GoldCoin gc : goldcoins) {
			gc.draw(g);
		}
		
		// draw silvercoins
		for(SilverCoin sc : silvercoins) {  
			sc.draw(g); 
			}
		
		for(VPotion vp : vpotions) { 
			vp.draw(g); 
			}
		
		for(HPotion hp : hpotions) {
			hp.draw(g);
			}
		
		// draw sparkles
		for(Sparkle s : sparkles) {
			s.draw(g);
		}
		
		// draw items
		for(Item i : items) {
			i.draw(g);
		}
		
		sw.draw(g);
		sw2.draw(g);
		tp1.draw(g);
		tp2.draw(g);
		
		// draw hud
		hud.draw(g);
		
		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for(int i = 0; i < boxes.size(); i++) {
			g.fill(boxes.get(i));
		}
		
	}
	
	public void handleInput() {
		if(KeyControl.isPressed(KeyControl.ESCAPE)) {
			gsm.setPaused(true);
		}
		if(blockInput) return;
		if(KeyControl.isDown(KeyControl.LEFT)) player.setLeft(); 
		if(KeyControl.isDown(KeyControl.RIGHT)) player.setRight();
		if(KeyControl.isDown(KeyControl.UP)) player.setUp();
		if(KeyControl.isDown(KeyControl.DOWN)) player.setDown();
		if(KeyControl.isPressed(KeyControl.SPACE))  if (player.hasWeapon()) { 
			player.shoot();
			gunshot = new AudioPlayer("/SFX/gunshot.wav");
			gunshot.play();
		}
		 
	}
	   
	//===============================================
	
	private void eventStart() {
		eventTick++;
		if(eventTick == 1) {
			boxes.clear();
			for(int i = 0; i < 22; i++) {
				boxes.add(new Rectangle(0, i * 16, GameLoop.WIDTH, 16));
			}
		}
		if(eventTick > 1 && eventTick < 32) {
			for(int i = 0; i < boxes.size(); i++) {
				Rectangle r = boxes.get(i);
				if(i % 2 == 0) {
					r.x -= 4;
				}
				else {
					r.x += 4;
				}
			}
		}
		if(eventTick == 33) {
			boxes.clear();
			eventStart = false;
			eventTick = 0;
		}
	}
	
	private void eventFinish() {
		eventTick++;
		if(eventTick == 1) {
			boxes.clear();
			for(int i = 0; i < 9; i++) {
				if(i % 2 == 0) boxes.add(new Rectangle(-128, i * 16, GameLoop.WIDTH, 16));
				else boxes.add(new Rectangle(128, i * 16, GameLoop.WIDTH, 16));
			}
		}
		if(eventTick > 1) {
			for(int i = 0; i < boxes.size(); i++) {
				Rectangle r = boxes.get(i);
				if(i % 2 == 0) {
					if(r.x < 0) r.x += 4;
				}
				else {
					if(r.x > 0) r.x -= 4;
				}
			}
		}
		if(eventTick > 33) {
				Data.setTime(player.getTicks());
				gsm.setState(GameStateManager.GAMEOVER);
				
			
		}
	}

}
