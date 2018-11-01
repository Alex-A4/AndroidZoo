# AndroidZoo
Non commercial android application about Yaroslavl's Zoo. This application shows my growth pace, new tools which I learned and embedded.

# Used library
* [JSOUP](https://jsoup.org/) used to parse zoo pages and to get necessary information, for example, list of news
* [Compressor](https://github.com/zetbaitsu/Compressor) used to compress images
* [Yandex mapkit](https://tech.yandex.ru/maps/mapkit/) used to show map

## Downloading
To download .apk file visit this [link](https://yadi.sk/d/HDeZ81O6U1lvTA)

## Compilation
To compile app you should add String API_KEY into the source code for using yandex mapkit. [Add](https://github.com/Alex-A4/AndroidZoo/blob/master/app/src/main/java/com/alexa4/pseudozoo/activities_package/MapContainer.java) it into onCreate() method when MapKitFactory is initializing
