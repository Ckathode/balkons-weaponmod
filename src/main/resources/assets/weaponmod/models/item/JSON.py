items = "battleaxe.diamond.png;boomerang.stone.png;dynamite.png;halberd.iron.png;knife.iron.png;shot.png;\
battleaxe.gold.png;boomerang.wood.png;firerod.png;halberd.stone.png;knife.stone.png;spear.diamond.png;\
battleaxe.iron.png;bullet.png;flail.diamond.png;halberd.wood.png;knife.wood.png;spear.gold.png;\
battleaxe.stone.png;cannonball.png;flail.gold.png;javelin.png;long_weapons.png;spear.iron.png;\
battleaxe.wood.png;cannon.png;flail.iron.png;jd-gui.cfg;musketbayonet.diamond.png;spear.stone.png;\
blowgun.png;crossbow-loaded.png;flail.stone.png;katana.diamond.png;musketbayonet.gold.png;spear.wood.png;\
blunderbuss.png;crossbow.png;flail-thrown.png;katana.gold.png;musketbayonet.iron.png;warhammer.diamond.png;\
blunder-ironpart.png;dart_damage.png;flail.wood.png;katana.iron.png;musketbayonet.png;warhammer.gold.png;\
bolt.png;dart_hunger.png;flintlock.png;katana.stone.png;musketbayonet.stone.png;warhammer.iron.png;\
boomerang.diamond.png;dart.png;gun-stock.png;katana.wood.png;musketbayonet.wood.png;warhammer.stone.png;\
boomerang.gold.png;dart_slow.png;halberd.diamond.png;knife.diamond.png;musket-ironpart.png;warhammer.wood.png;\
boomerang.iron.png;dummy.png;halberd.gold.png;knife.gold.png;musket.png".split(";")

for i in items:
    template = '''{
    "parent": "builtin/generated",
    "textures": {
        "layer0": "weaponmod:items/'''+i[:-4]+'''"
    },
    "display": {
        "thirdperson": {
            "rotation": [ -90, 0, 0 ],
            "translation": [ 0, 1, -3 ],
            "scale": [ 0.55, 0.55, 0.55 ]
        },
        "firstperson": {
            "rotation": [ 0, -135, 25 ],
            "translation": [ 0, 4, 2 ],
            "scale": [ 1.7, 1.7, 1.7 ]
        }
    }
}'''
    plik = open(i[:-4]+".json", 'w')
    plik.write(template)
    plik.close()
