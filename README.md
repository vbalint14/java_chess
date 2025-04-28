# Hagyományos Sakk Játék Program

**Specifikáció**  
**Készítette:** Venter Bálint

## 1. Játékmódok
A felhasználónak a sakk program különböző játékmódjait van lehetősége kipróbálnia, ezek:

- **0 személyes játékmód**: A játszmában 2 AI* egymás ellen játszik
- **1 személyes játékmód**: A játszmában 1 AI és 1 emberi felhasználó játszik egymás ellen
- **2 személyes játékmód**: A játszmában 2 emberi felhasználó játszik egymás ellen

> *(AI = gép által irányított játékos)*

## 2. Játék szabályok

### a) A játék célja
A sakk két játékos közötti stratégiai játék, amelynek célja az ellenfél királyának sakkmattolása.  
A sakk-matt olyan állapot, amikor a király támadás alatt van, és nincs szabályos lépés, amellyel a támadást kivédhetnénk.

### b) A játék menete
- A játék során a játékosok felváltva lépnek. A világos kezd, majd a sötét következik.
- Minden lépés során egy bábut mozgathatnak a sakktáblán a szabályoknak megfelelően.

### c) A bábuk és mozgásuk
A játékot két játékos játssza világos (fehér) és sötét (fekete) bábukkal.  
Minden játékos rendelkezik az alábbi bábukkal:
- 1 db király
- 1 db vezér (királynő)
- 2 db bástya
- 2 db futó
- 2 db huszár (ló)
- 8 db gyalog

A bábukat a (8x8 mezőből álló) sakktábla kezdő állás szerint helyezzük el: az első két sorban helyezkednek el a világos és sötét bábuk, a világos bábuk alul, a sötét bábuk pedig felül.

A királyok középen, a vezérek a király mellett, míg a többi bábu a sakktábla oldalai felé fokozatosan helyezkedik el.

#### i. Király:
- A király egy mezőt léphet bármilyen irányba (függőlegesen, vízszintesen vagy átlósan).
- A királyt nem szabad olyan mezőre lépni, ahol sakkban lenne (azaz ahol ellenfél bábujának támadása alatt áll).
- **Sáncolás**: A király és egy bástya közötti speciális lépés. Akkor végezhető el, ha mindkét bábu nem lépett korábban, a király nincs sakkban, és a király és bástya között nem áll másik bábu. A király két mezőt lép a bástya felé, a bástya pedig a király mellett helyezkedik el.

#### ii. Vezér:
- A vezér bármilyen irányban (vízszintesen, függőlegesen vagy átlósan) tetszőleges számú mezőt léphet.
- Nem ugorhat át más bábukat.

#### iii. Bástya:
- A bástya vízszintesen vagy függőlegesen tetszőleges számú mezőt léphet.
- Nem ugorhat át más bábukat.
- A bástya részt vehet a király sáncolásában.

#### iv. Futó:
- A futó átlósan léphet tetszőleges számú mezőt.
- Csak saját színű mezőkön mozoghat (az egyik mindig világos mezőkön, a másik mindig sötét mezőkön).
- Nem ugorhat át más bábukat.

#### v. Huszár:
- A huszár „L” alakú lépést tesz: két mezőt lép egy irányba (függőlegesen vagy vízszintesen), majd egyet jobbra vagy balra.
- A huszár az egyetlen bábu, amely átugorhat más bábukat.

#### vi. Gyalog:
- A gyalog egy mezőt léphet előre (csak a kezdő lépésénél léphet két mezőt).
- Csak átlósan üthet (balra vagy jobbra egy mezőt).
- **„En passant”** (fogás menet közben): Ha egy gyalog az ellenfél gyalogjának kezdő lépésére közvetlenül mellette landol, az ellenfél gyalogja azonnal megütheti őt, mintha csak egy mezőt lépett volna.
- **Promóció**: Ha egy gyalog eléri a sakktábla túloldalát, bármelyik másik bábura (vezér, bástya, futó vagy huszár) cserélhető.

### d) Sakk és sakk-matt
- **Sakk**: Amikor egy játékos úgy lép, hogy az ellenfél királyát támadás alá helyezi. Az ellenfélnek azonnal ki kell védenie a sakkot (a király elmozdításával, a támadó bábu leütésével vagy más bábuval való blokkolással).
- **Sakk-matt**: Ha a király sakkban van, és nincs szabályos lépés, amely kivédhetné a sakkot, a játék véget ér, és a sakk-mattot elszenvedő fél veszít.

### e) Döntetlenek
- **Patt**: Amikor a játékos lépésénél nincs szabályos lépés, de a király nincs sakkban. Ilyenkor a játék döntetlennel ér véget.
- **Draw Offer**: Ha csak a két király van már életben, akkor automatikus döntetlen lehetőség.
- **Megállapodás**: A játékosok kölcsönös megegyezéssel döntetlent köthetnek.

### f) Feladás
- A játékos bármikor feladhatja a játékot, ha úgy érzi, nem tud nyerni. Ebben az esetben az ellenfél nyer.

## 3. A program funkciói

### a) Felhasználói felület (GUI)
- **Tábla és Bábuk Megjelenítése**: A sakktábla és bábuk színkódolást alkalmaznak (világos és sötét mezők, világos és sötét bábuk).
- **Mozgatható Bábuk Kijelzése**: A kiválasztott bábu lehetséges lépéseit a program vizuálisan is kiemeli, például pontokkal vagy színes mezőkkel.
- **Játékmód Kiválasztása**: Az induló menüben választható a játékmód (0-, 1-, vagy 2-személyes), illetve új játék indítása vagy mentett játék betöltése.

### b) Mentés és Betöltés
- **Fájlformátum és Mentési Struktúra**: A játékállás mentése például `.json` vagy `.txt` formátumban történik, amely tartalmazza a sakktábla aktuális állását, a lépések számát és a játékosok típusát.
- **Betöltés**: Egy mentett fájlból történik, az adott játszma ott folytatódhat, ahol félbeszakadt (mentésre került).

### c) AI Játékmenet
- **AI Szintek és Beállítások**: Az AI egyszerű véletlenszerű lépések mellett opcionálisan összetettebb stratégiát is alkalmazhat.

### d) Hibakezelés
- **Érvénytelen Lépések Kezelése**: A program figyelmezteti a felhasználót, ha érvénytelen lépést próbál végrehajtani, és nem hajtja végre azt.
- **Sakk-matt és Patt Automatikus Ellenőrzése**: A program minden lépés után automatikusan ellenőrzi a sakk-matt vagy patt állapotot, és jelzi, ha ilyen helyzet áll fenn.

### e) Lépésszámláló és Lépéstörténet
- **Lépések rögzítése**: Egy oldalsó panelen vagy külön menüben, hogy a játékos visszanézhesse a korábbi lépéseket, amely gyakorlás és tanulás céljából hasznos.

---

