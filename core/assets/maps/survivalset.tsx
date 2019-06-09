<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.2" tiledversion="1.2.4" name="survivalset" tilewidth="64" tileheight="64" tilecount="6" columns="0">
 <grid orientation="orthogonal" width="1" height="1"/>
 <tile id="4">
  <properties>
   <property name="obstacle" type="bool" value="false"/>
  </properties>
  <image width="64" height="64" source="textures/grass01.png"/>
 </tile>
 <tile id="5">
  <properties>
   <property name="obstacle" type="bool" value="true"/>
  </properties>
  <image width="64" height="64" source="textures/rock01.png"/>
 </tile>
 <tile id="6">
  <image width="64" height="64" source="textures/grass_rock01.png"/>
 </tile>
 <tile id="7">
  <image width="64" height="64" source="textures/grass_rock02.png"/>
 </tile>
 <tile id="8">
  <image width="64" height="64" source="textures/grass_rock03.png"/>
 </tile>
 <tile id="9">
  <properties>
   <property name="obstacle" type="bool" value="false"/>
  </properties>
  <image width="64" height="64" source="origin/texture_corridor.png"/>
 </tile>
</tileset>
