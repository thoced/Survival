package com.mygdx.survival;

import box2dLight.ConeLight;
import com.badlogic.gdx.*;
import com.badlogic.gdx.ai.pfa.GraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.enemies.EnemyStage;
import com.mygdx.enemies.Monster;
import com.mygdx.lights.LightManagerSingleton;
import com.mygdx.path.NodeGraph;
import com.mygdx.path.WorldGraph;
import com.mygdx.physics.WorldManager;
import com.mygdx.stages.EntityStage;

import java.util.List;
import java.util.Random;

public class MainGdxClass extends Game implements InputProcessor{
	SpriteBatch batch;
	Texture img;

	private OrthographicCamera camera;
	private ScreenViewport viewport;
	private TmxMapLoader tmxMapLoader;
	private MapRender renderMap;
	Box2DDebugRenderer debugRenderer;

	IndexedAStarPathFinder pathFinder;
	private WorldGraph worldGraph;

	// opérator
	OperatorFree player;
	// Stage
	EntityStage entityStage;
	EnemyStage	enemyStage;

	// wordCoordinates
	private Vector3 worldCoordinates;
	private ConeLight coneLight;
	private float angleLight = 0f;
	private ShapeRenderer renderShape;


	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.ESCAPE) {
			this.dispose();
			System.exit(1);
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		worldCoordinates = viewport.getCamera().unproject(new Vector3(screenX,screenY,0));
		player.setDestination(worldCoordinates.x,worldCoordinates.y);
		return true;

	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		camerascrool = CAMERASCROLL.NONE;

		if(screenX > Gdx.graphics.getWidth() - 16) {
			camerascrool = CAMERASCROLL.RIGHT;
		}else if(screenX < 16)
			camerascrool = CAMERASCROLL.LEFT;

		if(screenY > Gdx.graphics.getHeight() - 16){
			camerascrool = CAMERASCROLL.TOP;
		}else if(screenY < 16)
			camerascrool = CAMERASCROLL.DOWN;

		// calcul de l'angle de direction de vue
		Vector3 viewDirection  = viewport.getCamera().unproject(new Vector3(screenX,screenY,0));
		player.setViewDirection(viewDirection.x,viewDirection.y);

		return true;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	enum CAMERASCROLL  {RIGHT,LEFT,TOP,DOWN,NONE};
	CAMERASCROLL camerascrool = CAMERASCROLL.NONE;
	final float cameraSpeed = 512f;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Graphics.DisplayMode displayMode = Gdx.graphics.getDisplayMode();
	//	Gdx.graphics.setFullscreenMode(displayMode);



		// RayHandler
		Box2D.init();
		WorldManager.getInstance();
		LightManagerSingleton.getInstance();

		/*PolygonShape polygonShape = new PolygonShape();
		polygonShape.setAsBox(8*32,1*32);
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(232,488);
		bodyDef.type = BodyDef.BodyType.StaticBody;

		Body body = LightManagerSingleton.getInstance().world.createBody(bodyDef);
		body.createFixture(polygonShape,1f);*/

	//	LightManagerSingleton.getInstance().addConeLight(128,128,2048,33,15,new Color(0.5f,0.2f,0.6f,1f));


		// initialisation camera
		viewport = new ScreenViewport();
		OrthographicCamera camera = new OrthographicCamera();
		camera.update();
		viewport.setCamera(camera);
		viewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		// ShapeRenderer
		renderShape = new ShapeRenderer();
		renderShape.setAutoShapeType(true);
		renderShape.setProjectionMatrix(viewport.getCamera().combined);

		// Stage
		entityStage = new EntityStage();
		entityStage.setViewport(viewport);
		// Stage ennemies
		enemyStage = EnemyStage.getInstance();
		enemyStage.setViewport(viewport);

		// chargement d'une map
		loadTiledMap(entityStage);
		// input Processor
		Gdx.input.setInputProcessor(this);

		// test path
		NodeGraph start = worldGraph.getNode(0,0);
		NodeGraph goal = worldGraph.getNode(50,50);

		GraphPath<NodeGraph> paths = worldGraph.findPath(start,goal);

		// opérator
		Texture textureOperator =  new Texture("textures/operator_atlas.png");
		List<TextureRegion> listsRegion = BaseActor.prepareRegion(textureOperator,256,256);
		player = new OperatorFree(textureOperator,listsRegion,"player");
		entityStage.addActor(player);

		PlayerSingleton.getInstance().player = player;

		debugRenderer = new Box2DDebugRenderer();

		// test load enemies
		this.testLoadEnemies();


	}

	public void testLoadEnemies(){

		Texture texture = new Texture("textures/operator_atlas.png");
		List<TextureRegion> listsRegion = BaseActor.prepareRegion(texture,256,256);

		for(int i=0;i<16;i++) {
			Random random = new Random();
			int massAdd = random.nextInt(10);
			int speedAdd = random.nextInt(8);

			Monster monster = new Monster(texture, listsRegion, "monster",75 + massAdd);
			monster.setPosition(128 + (i * 64), 768);
			enemyStage.addActor(monster);
			monster.setDestination(3122,2048);
		}

	}


	private void loadTiledMap(Stage stage) {
		tmxMapLoader = new TmxMapLoader();
		TiledMap tiledMap = tmxMapLoader.load("maps/map01.tmx");
		TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(0);
		renderMap = new MapRender(tiledMap,batch);

		for(int i=0;i<tiledMap.getLayers().size();i++){
			System.out.println(tiledMap.getLayers().get(i).getName());
		}

		// creation du PathFinding
		// Création du monde
		int mapWidth  = layer.getWidth();
		int mapHeight = layer.getHeight();
		worldGraph = WorldGraph.getInstance();
		worldGraph.prepareWorldGraph(mapWidth,mapHeight);


		for(int y=0;y<mapHeight;y++) {
			for (int x = 0; x < mapWidth; x++) {
				boolean obstacle = false;
				if(layer.getCell(x,y).getTile().getProperties().containsKey("obstacle") && ((Boolean)layer.getCell(x,y).getTile().getProperties().get("obstacle")) == true){
					WorldManager.getInstance().addWalk(x,y);
					continue;
				}
				else {
					obstacle = false;
					NodeGraph nodeGraph = new NodeGraph(x,y,obstacle);
					worldGraph.addNodeGraph(nodeGraph);

				}
			}
		}

		// création des connections
		worldGraph.connectNodes();

		// génération des lumière
		LightManagerSingleton.getInstance().loadLightsLayer(tiledMap.getLayers().get("lights"),stage);

	}


	@Override
	public void render () {

		float delta = Gdx.graphics.getDeltaTime();
		// update
		cameraUpdate(delta);
		entityStage.act(delta);
		enemyStage.act(delta);
		WorldManager.getInstance().world.step(delta,6,2);

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderMap.setView((OrthographicCamera) viewport.getCamera());
		renderMap.render();
		// affichage stage
		entityStage.setViewport(viewport);
		entityStage.draw();
		enemyStage.setViewport(viewport);
		enemyStage.draw();
		// shape

		renderShape.begin(ShapeRenderer.ShapeType.Line);
		renderShape.setColor(Color.CYAN);
		//renderShape.rect(0,0,256,256);
		renderShape.end();

		LightManagerSingleton.getInstance().rayHandler.setCombinedMatrix((OrthographicCamera) viewport.getCamera());
		LightManagerSingleton.getInstance().rayHandler.updateAndRender();

		debugRenderer.render(WorldManager.getInstance().world,viewport.getCamera().combined);

		//angleLight+=delta * 128f;
		//coneLight.setDirection(angleLight);

	//	float a = coneLight.getDirection();
	//	coneLight.setDirection(a);



	}
	
	@Override
	public void dispose () {
		batch.dispose();
		renderMap.dispose();
		entityStage.dispose();
	}

	private void cameraUpdate(float delta) {

		final float val = delta * cameraSpeed;

		switch (camerascrool){
			case RIGHT: viewport.getCamera().position.add(val,0,0);
				break;
			case LEFT: viewport.getCamera().position.sub(val,0,0);
				break;
			case TOP: viewport.getCamera().position.sub(0,val,0);
				break;
			case DOWN: viewport.getCamera().position.add(0,val,0);
				break;

		}
		// update Camera
		viewport.getCamera().update();
	}
}
