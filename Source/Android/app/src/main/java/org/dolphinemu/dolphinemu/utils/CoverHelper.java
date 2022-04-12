// SPDX-License-Identifier: GPL-2.0-or-later

package org.dolphinemu.dolphinemu.utils;

import android.graphics.Bitmap;

import org.dolphinemu.dolphinemu.model.GameFile;

import java.io.FileOutputStream;

public final class CoverHelper
{
  public static String buildGameTDBUrl(GameFile game, String region)
  {
    final String baseUrl = "https://art.gametdb.com/wii/cover/%s/%s.png";
    String id = game.getGameTdbId();
    if (region == null)
    {
      region = getRegion(game);
    }
    else
    {
      id = toRegion(id, region);
    }
    return String.format(baseUrl, region, id);
  }

  private static String toRegion(String id, String region)
  {
    if (id == null || id.length() < 4)
    {
      // ignore
    }
    else if ("JA".equals(region))
    {
      if(id.charAt(3) != 'J')
      {
        return id.substring(0, 3) + "J" + id.substring(4);
      }
    }
    else if ("US".equals(region))
    {
      if(id.charAt(3) != 'E')
      {
        return id.substring(0, 3) + "E" + id.substring(4);
      }
    }
    return id;
  }

  public static String getRegion(GameFile game)
  {
    String region;
    switch (game.getRegion())
    {
      case 0: // NTSC_J
        region = "JA";
        break;
      case 1: // NTSC_U
        region = "US";
        break;
      case 4: // NTSC_K
        region = "KO";
        break;
      case 2: // PAL
        switch (game.getCountry())
        {
          case 3: // Australia
            region = "AU";
            break;
          case 4: // France
            region = "FR";
            break;
          case 5: // Germany
            region = "DE";
            break;
          case 6: // Italy
            region = "IT";
            break;
          case 8: // Netherlands
            region = "NL";
            break;
          case 9: // Russia
            region = "RU";
            break;
          case 10: // Spain
            region = "ES";
            break;
          case 0: // Europe
          default:
            region = "EN";
            break;
        }
        break;
      case 3: // Unknown
      default:
        region = "EN";
        break;
    }
    return region;
  }

  public static void saveCover(Bitmap cover, String path)
  {
    try
    {
      FileOutputStream out = new FileOutputStream(path);
      cover.compress(Bitmap.CompressFormat.PNG, 100, out);
      out.close();
    }
    catch (Exception ignored)
    {
    }
  }
}
