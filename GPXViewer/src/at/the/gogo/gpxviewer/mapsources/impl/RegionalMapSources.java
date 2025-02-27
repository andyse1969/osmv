package at.the.gogo.gpxviewer.mapsources.impl;

import at.the.gogo.gpxviewer.mapsources.adapter.BaseTileProvider;
import at.the.gogo.gpxviewer.mapsources.adapter.GoogleTileCache;

/**
 * Map sources that do not cover the whole world
 */
public class RegionalMapSources {

	public RegionalMapSources() {
	}

	// /**
	// * Mapa Polski, Europy i Świata - DoCelu.pl (added by "maniek-ols") <p>
	// <a
	// * href="docelu.pl">docelu.pl</a> </p>
	// */
	// public static class DoCeluPL extends AbstractMapSource implements
	// UpdatableMapSource {
	//
	// private String baseUrl;
	//
	// public DoCeluPL() {
	// super("DoCeluPL", 2, 16, "png", TileUpdate.LastModified);
	// update();
	// }
	//
	// @Override
	// public void update() {
	// baseUrl = MapSourceTools.loadMapUrl(this, "baseurl");
	// }
	//
	// @Override
	// public String getTileUrl(int zoom, int tilex, int tiley) {
	// String sx = String.format("%06x", tilex);
	// String sy = String.format("%06x", tiley);
	// char[] cx = sx.toCharArray();
	// char[] cy = sy.toCharArray();
	// String szoom = Integer.toHexString(zoom);
	//
	// String s = baseUrl + szoom + "/" + cx[4] + cy[4] + "/" + cx[3] + cy[3] +
	// "/" + cx[2]
	// + cy[2] + "/" + cx[1] + cy[1] + "/" + cx[0] + cy[0] + "/z" + szoom + "x"
	// + sx
	// + "y" + sy + ".png";
	// return s;
	// }
	//
	// @Override
	// public String toString() {
	// return "Docelu.pl (Poland only)";
	// }
	// }
	//
	// /**
	// * Darmowa Mapa Polski dla GPS Garmin - UMP-pcPL (added by "maniek-ols")
	// <p>
	// * <a href="http://ump.waw.pl">ump.waw.pl</a> </p>
	// */
	// public static class UmpWawPl extends AbstractMapSource {
	//
	// private static int SERVER_NUM = 0;
	// private static final int MAX_SERVER_NUM = 4;
	//
	// public UmpWawPl() {
	// super("UMP-pcPL", 0, 18, "png", TileUpdate.LastModified);
	// }
	//
	// @Override
	// public String getTileUrl(int zoom, int tilex, int tiley) {
	// String s = "http://" + SERVER_NUM + ".tiles.ump.waw.pl/ump_tiles/" + zoom
	// + "/" + tilex
	// + "/" + tiley + ".png";
	// SERVER_NUM = (SERVER_NUM + 1) % MAX_SERVER_NUM;
	// return s;
	// }
	//
	// @Override
	// public String toString() {
	// return getName() + " (Poland only)";
	// }
	// }
	//
	public static class OutdooractiveGermany extends BaseTileProvider {

		private static int SERVER_NUM = 0;
		protected String mapName = "portal";
		protected String name = "Outdooractive.com (Germany only)";

		public OutdooractiveGermany(GoogleTileCache tileCache) {
			super(9, 17, tileCache);
		}

		@Override
		public String getTileUrlString(int tilex, int tiley, int zoom) {
			if (zoom < 8) {
				throw new RuntimeException("Zoom level not suported");
			}
			// String s = "http://t" + SERVER_NUM + ".outdooractive.com/" +
			// mapName + "/map/" + zoom
			// + "/" + tilex + "/" + tiley + ".png";
			String s = "http://s" + SERVER_NUM + ".outdooractive.com/"
					+ mapName + "/map/" + zoom + "/" + tilex + "/" + tiley
					+ ".png";

			// SERVER_NUM = (SERVER_NUM + 1) % 4;
			SERVER_NUM = (SERVER_NUM + 1) % 4;
			return s;
		}

		@Override
		public String getMapName() {
			return name;
		}
	}

	public static class OutdooractiveSouthTyrol extends OutdooractiveGermany {

		public OutdooractiveSouthTyrol(GoogleTileCache tileCache) {
			super(tileCache);
			name = "Outdooractive SouthTyrol";
			mapName = "suedtirol";

		}

	}

	public static class OutdooractiveAustria extends OutdooractiveGermany {

		public OutdooractiveAustria(GoogleTileCache tileCache) {
			super(tileCache);
			name = "Outdooractive Austria";
			mapName = "austria";
		}
	}

	// /**
	// * CykloServer http://www.cykloserver.cz/cykloatlas/index.php
	// */
	// public static class Cykloatlas extends AbstractMapSource {
	//
	// public Cykloatlas() {
	// super("Cykloatlas", 7, 15, "png", TileUpdate.LastModified);
	// }
	//
	// @Override
	// public String getTileUrl(int zoom, int tilex, int tiley) {
	// String z = Integer.toString(zoom);
	// if (zoom >= 13) {
	// z += "c";
	// }
	// return "http://services.tmapserver.cz/tiles/gm/shc/" + z + "/" + tilex +
	// "/" + tiley
	// + ".png";
	// }
	//
	// @Override
	// public String toString() {
	// return getName() + " (CZ, SK)";
	// }
	// }
	//
	// /**
	// * Requires known user agent, and something else otherwise we get only a
	// * HTTP 403 <p> map provider does not work --> currently unused </p>
	// */
	// public static class TuristikaMapSk extends AbstractMapSource {
	//
	// public TuristikaMapSk() {
	// super("TuristikaMapSk (Slovakia)", 12, 15, "png");
	// }
	//
	// @Override
	// public String getTileUrl(int zoom, int tilex, int tiley) {
	// String sx = String.format("%09d", tilex);
	// String sy = String.format("%09d", tiley);
	// sx = sx.substring(0, 3) + "/" + sx.substring(3, 6) + "/" +
	// sx.substring(6, 9);
	// sy = sy.substring(0, 3) + "/" + sy.substring(3, 6) + "/" +
	// sy.substring(6, 9);
	//
	// String s = "http://www.turistickamapa.sk/tiles/sr50/" + zoom + "/" + sx +
	// "/" + sy
	// + ".png";
	// System.out.println(s);
	// return s;
	// }
	// }
	//
	// /**
	// * @author SourceForge.net user didoa.
	// */
	// public static class FreemapSlovakia extends AbstractMapSource {
	//
	// public FreemapSlovakia() {
	// super("FreemapSlovakia", 5, 16, "png", TileUpdate.None);
	// }
	//
	// @Override
	// public String getTileUrl(int zoom, int tilex, int tiley) {
	// return "http://www.freemap.sk/layers/allinone/?/BNp/" + zoom + "/" +
	// tilex + "/"
	// + tiley;
	// }
	//
	// @Override
	// public String toString() {
	// return "Freemap Slovakia (Atlas)";
	// }
	// }
	//
	// /**
	// * @author SourceForge.net user didoa.
	// */
	// public static class FreemapSlovakiaHikingHillShade extends
	// AbstractMapSource {
	//
	// public FreemapSlovakiaHikingHillShade() {
	// super("FreemapSlovakiaHikingHillShade", 6, 16, "png", TileUpdate.None);
	// }
	//
	// @Override
	// public String getTileUrl(int zoom, int tilex, int tiley) {
	// return "http://www.freemap.sk/layers/allinone/?/BVRNTp/" + zoom + "/" +
	// tilex + "/"
	// + tiley;
	// }
	//
	// @Override
	// public String toString() {
	// return "Freemap Slovakia Hiking (with HillShade)";
	// }
	// }
	//
	// /**
	// * @author SourceForge.net user didoa.
	// */
	// public static class FreemapSlovakiaHiking extends AbstractMapSource {
	//
	// public FreemapSlovakiaHiking() {
	// super("FreemapSlovakiaHiking", 6, 16, "png", TileUpdate.None);
	// }
	//
	// @Override
	// public String getTileUrl(int zoom, int tilex, int tiley) {
	// return "http://www.freemap.sk/layers/allinone/?/BNTNp/" + zoom + "/" +
	// tilex + "/"
	// + tiley;
	// }
	//
	// @Override
	// public String toString() {
	// return "Freemap Slovakia Hiking";
	// }
	// }
	//
	// /**
	// * Emapi - mapa internetowa firmy Emapa <p> <a
	// * href="http://emapi.pl/">emapi.pl</a> </p>
	// */
	// public static class EmapiPl extends AbstractMapSource {
	//
	// public EmapiPl() {
	// super("EmapiPl", 0, 19, "png", TileUpdate.None);
	// }
	//
	// @Override
	// public String getTileUrl(int zoom, int x, int y) {
	// return "http://emapi.pl/Default.aspx?tileX=" + x + "&tileY=" + y +
	// "&zoom=" + zoom
	// + "&layer=std&fun=GetMap&userID=pasat";
	// }
	//
	// @Override
	// public HttpURLConnection getTileUrlConnection(int zoom, int tilex, int
	// tiley)
	// throws IOException {
	// HttpURLConnection conn = super.getTileUrlConnection(zoom, tilex, tiley);
	// conn.addRequestProperty("Cookie", "currentView=");
	// conn.addRequestProperty("Referer", "http://emapi.pl/?referer=");
	// return conn;
	// }
	//
	// @Override
	// public String toString() {
	// return "Emapi.pl (Poland only)";
	// }
	// }
	//
	// public static class NearMap extends AbstractMapSource {
	//
	// public NearMap() {
	// super("NearMap Australia", 0, 21, "jpg", TileUpdate.IfModifiedSince);
	// }
	//
	// @Override
	// public String getTileUrl(int zoom, int tilex, int tiley) {
	// return "http://www.nearmap.com/maps/hl=en&nml=Vert&z=" + zoom + "&x=" +
	// tilex + "&y="
	// + tiley;
	// }
	// }
	//
	// /**
	// * Hubermedia Bavaria map http://maps.hubermedia.de/
	// */
	// public static class HubermediaBavaria extends AbstractMapSource {
	//
	// final String[] mapUrls;
	// int serverNum = 0;
	//
	// public HubermediaBavaria() {
	// super("Hubermedia Bavaria", 10, 16, "png", TileUpdate.IfNoneMatch);
	// mapUrls = new String[17];
	//
	// mapUrls[10] = "http://t0.hubermedia.de/TK500/DE/Bayern/";
	// mapUrls[11] = mapUrls[10];
	// mapUrls[12] =
	// "http://t{$servernum}.wms.hubermedia.de/tk200/de/bayern//Z{$z}/{$y}/{$x}.png";
	// mapUrls[13] =
	// "http://t{$servernum}.hubermedia.de/TK50/DE/Bayern//Z{$z}/{$y}/{$x}.png";
	// mapUrls[14] = mapUrls[13];
	// mapUrls[15] =
	// "http://t{$servernum}.hubermedia.de/TK25/DE/Bayern//Z{$z}/{$y}/{$x}.png";
	// mapUrls[16] =
	// "http://t{$servernum}.hubermedia.de/DOK/DE/Bayern//Z{$z}/{$y}/{$x}.png";
	// }
	//
	// @Override
	// public String getTileUrl(int zoom, int tilex, int tiley) {
	// serverNum = (serverNum + 1) % 3;
	// if (zoom >= 12) {
	// return MapSourceTools.formatMapUrl(mapUrls[zoom], serverNum, zoom, tilex,
	// tiley);
	// } else {
	// String tc = MapSourceTools.encodeQuadTree(zoom, tilex, tiley);
	// return mapUrls[zoom] + zoom + "/" + tc + ".png";
	// }
	// }
	// }
	//
	// /**
	// * http://www.statkart.no/
	// * <p/>
	// * <p> There is a limit of 10 000 cache-tiler per end user (unique IP
	// * address) per day. This restriction is therefore not associated with the
	// * individual application. </p>
	// * <p/>
	// * <table border="1"> <tr> <th>Service Name</th> <th>Underlying WMS
	// * service</th> <th>Teams from WMS</th> <th>Maximum zoom level</th> </tr>
	// * <tr> <td>kartdata2</td> <td>Kartdata2 WMS</td> <td>all</td> <td>12</td>
	// * </tr> <tr> <td>sjo_hovedkart2</td> <td>See chart master map series 2
	// * WMS</td> <td>all</td> <td>17</td> </tr> <tr> <td>topo2</td> <td>Norway
	// * Topographic map 2 WMS</td> <td>all</td> <td>17 <tr>
	// * <td>topo2graatone</td> <td>Norway Topographic map 2 grayscale WMS</td>
	// * <td>all</td> <td>17</td> </tr> <tr> <td>toporaster2</td>
	// <td>Topographic
	// * raster map 2 WMS</td> <td>all</td> <td>17</td> </tr> <tr>
	// <td>europa</td>
	// * <td>Europe Map WMS</td> <td>all</td> <td>17</td> </tr> </table>
	// * <p/>
	// * <
	// * pre>
	// *
	// http://www.statkart.no/?module=Articles;action=Article.publicShow;ID=14165
	// * </pre>
	// */
	// public static class StatkartTopo2 extends AbstractMapSource {
	//
	// final String service;
	//
	// public StatkartTopo2() {
	// this("topo2", "Statkart Topo 2", 0, 17, "png", TileUpdate.None);
	// }
	//
	// public StatkartTopo2(String service, String name, int minZoom, int
	// maxZoom,
	// String tileType, TileUpdate tileUpdate) {
	// super(name, minZoom, maxZoom, tileType, tileUpdate);
	// this.service = service;
	// }
	//
	// @Override
	// public String toString() {
	// return getName() + " (Norway)";
	// }
	//
	// @Override
	// public String getTileUrl(int zoom, int tilex, int tiley) {
	// return "http://opencache.statkart.no/gatekeeper/gk/gk.open_gmaps?layers="
	// + service
	// + "&zoom=" + zoom + "&x=" + tilex + "&y=" + tiley;
	// }
	// }
	//
	// public static class StatkartToporaster2 extends StatkartTopo2 {
	//
	// public StatkartToporaster2() {
	// super("toporaster2", "Statkart Toporaster 2", 0, 17, "png",
	// TileUpdate.None);
	// }
	// }
	//
	// public static class EniroComMap extends AbstractMapSource {
	//
	// private final String mapType;
	//
	// public EniroComMap() {
	// this("map");
	// }
	//
	// protected EniroComMap(String mapType) {
	// super("Eniro.com-" + mapType, 0, 20, "png", TileUpdate.IfModifiedSince);
	// this.mapType = mapType;
	// }
	//
	// @Override
	// public String toString() {
	// return "Eniro Map (SE, NO, FI)";
	// }
	//
	// @Override
	// public String getTileUrl(int zoom, int tilex, int tiley) {
	// int y = (1 << zoom) - 1 - tiley;
	// return "http://map.eniro.com/geowebcache/service/tms1.0.0/" + mapType +
	// "/" + zoom
	// + "/" + tilex + "/" + y + ".png";
	// }
	// }
	//
	// public static class EniroComNautical extends EniroComMap {
	//
	// public EniroComNautical() {
	// super("nautical");
	// }
	//
	// @Override
	// public String toString() {
	// return "Eniro Nautical (SE, NO, FI)";
	// }
	// }
	//
	// public static class EniroComAerial extends EniroComMap {
	//
	// public EniroComAerial() {
	// super("aerial");
	// }
	//
	// @Override
	// public String toString() {
	// return "Eniro Aerial (SE, NO, FI)";
	// }
	// }
	//
	// /**
	// * http://www.mapplus.ch/
	// */
	// public static class MapplusCh extends AbstractMapSource {
	//
	// final String referer;
	// private static final char[] hex = {'0', '1', '2', '3', '4', '5', '6',
	// '7', '8', '9', 'a',
	// 'b', 'c', 'd', 'e', 'f'};
	//
	// public MapplusCh() {
	// super("MapplusCh", 7, 16, "jpg", TileUpdate.ETag);
	// char[] sessID = new char[32];
	// Random rnd = new Random();
	// for (int i = 0; i < sessID.length; i++) {
	// sessID[i] = hex[rnd.nextInt(hex.length)];
	// }
	// // example sessID = "12ea56827487e927d4b202ad48248109";
	// referer =
	// "http://www.mapplus.ch/NeapoljsMapPage.php?uid=public&group=public&sessID="
	// + new String(sessID);
	// }
	//
	// @Override
	// public String toString() {
	// return "Map+ (Switzerland)";
	// }
	//
	// @Override
	// public String getTileUrl(int zoom, int tilex, int tiley) {
	// int z = 17 - zoom;
	// return "http://mp2.mapplus.ch/kacache/" + z + "/def/def/t" + tiley + "/l"
	// + tilex
	// + "/t" + tiley + "l" + tilex + ".jpg";
	// }
	//
	// @Override
	// public HttpURLConnection getTileUrlConnection(int zoom, int tilex, int
	// tiley)
	// throws IOException {
	// HttpURLConnection conn = super.getTileUrlConnection(zoom, tilex, tiley);
	// // http request property "Referer" is required -
	// // otherwise we only get "tranparentpixel.gif"
	// conn.setRequestProperty("Referer", referer);
	// return conn;
	// }
	// }

	/**
     *
     *
     *
     */
	public static class Bergfex extends BaseTileProvider {

		/**
		 * 2009-02-20: server 4 causes some problems - commented out
		 */
		static final byte[] SERVER_IDS = { 4, 5, 6, 7, 8 };// {1, 2, /*3 ,*/ 4,
															// 5};
		int SERVERNUM = 0;

		public Bergfex(GoogleTileCache tileCache) {
			super(8, 15, tileCache);
		}

		@Override
		public String getMapName() {

			return "Bergfex";
		}

		@Override
		public String getTileUrlString(int tilex, int tiley, int zoom) {
			String baseurl = "http://static" + SERVER_IDS[SERVERNUM]
					+ ".bergfex.at/images/amap/";
			SERVERNUM = (SERVERNUM + 1) % SERVER_IDS.length;
			String xBase = "";
			if (zoom > 13) {
				xBase = Integer.toString(tilex).substring(0, zoom - 12) + "/";
			}
			return baseurl + zoom + "/" + xBase + zoom + "_" + tilex + "_"
					+ tiley + ".png";
		}
	}

	public static class MyTopo extends BaseTileProvider {

		public MyTopo(GoogleTileCache tileCache) {
			super(2, 15, tileCache);
		}

		@Override
		public String getMapName() {
			return "My Topo";
		}

		@Override
		public String getTileUrlString(int tilex, int tiley, int zoom) {
			String baseurl = "http://maps.mytopo.com/groundspeak/tilecache.py/1.0.0/topoG/";

			return baseurl + zoom + "/" + tilex + "/" + tiley + ".png";
		}

		// @Override
		// public HttpURLConnection getTileUrlConnection(int zoom, int tilex,
		// int tiley)
		// throws IOException {
		// HttpURLConnection conn = super.getTileUrlConnection(zoom, tilex,
		// tiley);
		// conn.addRequestProperty("Referer", "http://www.mytopo.com/maps/");
		// return conn;
		// }
	}

	// public static class ICAOMapsGermany extends AbstractMapSource {
	//
	// public ICAOMapsGermany() {
	// super("ICAOMapsGermany", 4, 11, "jpg", TileUpdate.IfNoneMatch);
	// }
	//
	// @Override
	// public String toString() {
	// return "ICAO Maps (Germany)";
	// }
	//
	// @Override
	// public String getTileUrl(int zoom, int x, int y) {
	// int tms_y = -1 * (y - ((int) Math.pow(2.0, zoom) - 1));
	// return "http://vfr-bulletin.de/maps/ICAO/" + zoom + "/" + x + "/" + tms_y
	// + ".jpg";
	// }
	// }
}
