package com.doodlegames.air.force.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public abstract class TextureAtlasLoader extends SynchronousAssetLoader<TextureAtlas, TextureAtlasLoader.TextureAtlasParameter> {

   TextureAtlas.TextureAtlasData data;


   public TextureAtlasLoader(FileHandleResolver var1) {
      super(var1);
   }

   public <T> Array<AssetDescriptor<T>> getDependencies(final String s, final TextureAtlasParameter textureAtlasParameter) {

       final FileHandle resolve = this.resolve(s);

       final FileHandle parent = resolve.parent();

       if (textureAtlasParameter != null) {

           this.data = new TextureAtlas.TextureAtlasData(resolve, parent, textureAtlasParameter.flip);

       }

       else {

           this.data = new TextureAtlas.TextureAtlasData(resolve, parent, false);

       }

       final Array<T> array = new Array();

       for (TextureAtlas.TextureAtlasData.Page page : this.data.getPages()) {

           final FileHandle resolve2 = this.resolve(page.textureFile.path());

           final TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter();

           textureParameter.format = Pixmap.Format.RGBA4444;

           textureParameter.genMipMaps = page.useMipMaps;

           textureParameter.minFilter = page.minFilter;

           textureParameter.magFilter = page.magFilter;

           array.add((T) new AssetDescriptor(resolve2.path().replaceAll("\\\\", "/"), Texture.class, textureParameter));

       }

       return (Array<AssetDescriptor<T>>) array;

   }

   

   public TextureAtlas load(final AssetManager assetManager, final String s, final TextureAtlasParameter textureAtlasParameter) {

       for (TextureAtlas.TextureAtlasData.Page page : this.data.getPages()) {

           page.texture = (Texture)assetManager.get(page.textureFile.path().replaceAll("\\\\", "/"), Texture.class);

       }

       return new TextureAtlas(this.data);

   }

   public static class TextureAtlasParameter extends AssetLoaderParameters<TextureAtlas> {

      public boolean flip = false;


      public TextureAtlasParameter() {}

      public TextureAtlasParameter(boolean var1) {
         this.flip = var1;
      }
   }
}
