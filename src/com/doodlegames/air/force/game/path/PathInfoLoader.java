package com.doodlegames.air.force.game.path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Iterator;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.doodlegames.air.force.utils.Settings;

public class PathInfoLoader
		extends
		SynchronousAssetLoader<EnemyMutiMove.IPathInfoSet, PathInfoLoader.PathInfoParameter> {

	private static Hashtable<String, Class<? extends AbstractEnemyPath.PathInfo>> classTable;

	public PathInfoLoader(FileHandleResolver var1) {
		super(var1);
	}

	private static Class<? extends AbstractEnemyPath.PathInfo> getClassByName(
			String var0) {
		if (classTable == null) {
			classTable = new Hashtable();
			classTable.put("LinePathInfo", EnemyLineMove.LinePathInfo.class);
			classTable.put("BezierPathInfo",
					EnemyBezierMove.BezierPathInfo.class);
			classTable.put("ArcPathInfo", EnemyArcMove.ArcPathInfo.class);
			classTable.put("MutiPathInfo", EnemyMutiMove.MutiPathInfo.class);
		}

		return (Class) classTable.get(var0);
	}

	public static EnemyMutiMove.IPathInfoSet getPathInfoSetByHandle(
			final FileHandle fileHandle) {

		PathInfoSetData pathInfoSetData = null;

		BufferedReader bufferedReader;

		BufferedReader bufferedReader2 = null;

		int i = 0;

		final Object o;

		Throwable t = null;

		AbstractEnemyPath.PathInfo pathInfo = null;

		final Object o2 = null;

		IOException ex3;

		final Object o4;

		final Object o3;

		final Throwable t2;

		while (true) {

			pathInfoSetData = new PathInfoSetData();

			bufferedReader = null;

			try {

				pathInfoSetData.infos.clear();

				bufferedReader2 = new BufferedReader(new InputStreamReader(
						fileHandle.read()));

			}

			catch (Exception ex) {
			}

			finally {

				if (bufferedReader != null) {

					try {
						bufferedReader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				try {

					i = Integer.parseInt(bufferedReader2.readLine());

				}

				catch (Exception ex) {

					bufferedReader = bufferedReader2;

					Settings.w(t.toString());

					pathInfoSetData.infos.clear();

					if (bufferedReader == null) {

						break;

					}

					try {
						bufferedReader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				while (i > 0) {

					try {
						pathInfo = (AbstractEnemyPath.PathInfo) getClassByName(
								bufferedReader2.readLine()).newInstance();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					pathInfo.deSerialize(bufferedReader2);

					--i;

					pathInfoSetData.infos.add(pathInfo);

					// try {}
					//
					// catch (IOException ex) {
					//
					// Settings.w(ex.toString());
					//
					// }

					// break Label_0117;

				}

				try {

					bufferedReader2.close();

				}

				catch (Exception ex) {
				}

				Label_0184:

				{

					if (bufferedReader2 == null) {

						break Label_0184;

					}

					try {

						bufferedReader2.close();

						break;

					}

					catch (IOException ex2) {

						Settings.w(ex2.toString());

						break;

					}

				}

			}

		}

		for (AbstractEnemyPath.PathInfo pathInfo2 : pathInfoSetData.infos) {

			if (pathInfo2 instanceof EnemyMutiMove.MutiPathInfo) {

				((EnemyMutiMove.MutiPathInfo) pathInfo2)
						.fillPathInfos(pathInfoSetData);

			}

		}

		return pathInfoSetData;

		// o3 = o4;
		//
		// bufferedReader = bufferedReader2;
		//
		// // goto Label_0215;
		//
		// t = t2;
		//
		// bufferedReader = null;
		//
		// break Label_0095;

	}

	public Array<AssetDescriptor> getDependencies(String var1,
			PathInfoLoader.PathInfoParameter var2) {
		return null;
	}

	public EnemyMutiMove.IPathInfoSet load(AssetManager var1, String var2,
			PathInfoLoader.PathInfoParameter var3) {
		return getPathInfoSetByHandle(this.resolve(var2));
	}

	public static class PathInfoParameter extends
			AssetLoaderParameters<EnemyMutiMove.IPathInfoSet> {

	}

	private static class PathInfoSetData implements EnemyMutiMove.IPathInfoSet {

		public Array<AbstractEnemyPath.PathInfo> infos;

		private PathInfoSetData() {
			this.infos = new Array(10);
		}

		public void addPathInfo(AbstractEnemyPath.PathInfo var1) {
			if (var1 != null && !this.infos.contains(var1, true)) {
				for (int var2 = 0; var2 < this.infos.size; ++var2) {
					if (var1.pathName == ((AbstractEnemyPath.PathInfo) this.infos
							.get(var2)).pathName) {
						Settings.w("exist pathName in pathInfos:"
								+ var1.pathName);
						return;
					}
				}

				this.infos.add(var1);
			}
		}

		public AbstractEnemyPath.PathInfo getPathInfo(int var1) {
			return var1 >= 0 && var1 < this.infos.size ? (AbstractEnemyPath.PathInfo) this.infos
					.get(var1) : null;
		}

		public AbstractEnemyPath.PathInfo getPathInfo(String var1) {
			Iterator var2 = this.infos.iterator();

			AbstractEnemyPath.PathInfo var3;
			do {
				if (!var2.hasNext()) {
					return null;
				}

				var3 = (AbstractEnemyPath.PathInfo) var2.next();
			} while (!var3.pathName.equals(var1));

			return var3;
		}

		public int getPathInfoLength() {
			return this.infos.size;
		}
	}
}
