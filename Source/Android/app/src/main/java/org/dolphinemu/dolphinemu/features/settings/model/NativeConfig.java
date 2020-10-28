package org.dolphinemu.dolphinemu.features.settings.model;

public class NativeConfig
{
  public static final int LAYER_BASE_OR_CURRENT = 0;
  public static final int LAYER_LOCAL_GAME = 1;
  public static final int LAYER_ACTIVE = 2;

  public static native boolean isSettingSaveable(String file, String section, String key);

  public static native void loadGameInis(String gameId, int revision);

  public static native void unloadGameInis();

  public static native void save(int layer);

  /**
   * MMJR: Native methods to apply settings directly to Core
   */
  public static native int[] getEditableSettings();
  public static native void setEditableSettings(int[] settings);

  public static native boolean isOverridden(String file, String section, String key);

  public static native boolean deleteKey(int layer, String file, String section, String key);

  public static native String getString(int layer, String file, String section, String key,
          String defaultValue);

  public static native boolean getBoolean(int layer, String file, String section, String key,
          boolean defaultValue);

  public static native int getInt(int layer, String file, String section, String key,
          int defaultValue);

  public static native float getFloat(int layer, String file, String section, String key,
          float defaultValue);

  public static native void setString(int layer, String file, String section, String key,
          String value);

  public static native void setBoolean(int layer, String file, String section, String key,
          boolean value);

  public static native void setInt(int layer, String file, String section, String key, int value);

  public static native void setFloat(int layer, String file, String section, String key,
          float value);
}
