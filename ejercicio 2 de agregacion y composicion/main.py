class Pantalla:
    def __init__(self, tipo, ancho, alto):
        self.tipo = tipo
        self.ancho = ancho
        self.alto = alto

    def __str__(self):
        return f"{self.tipo} {self.ancho}x{self.alto}"


class Aplicacion:
    def __init__(self, nombre, tamanio):
        self.nombre = nombre
        self.tamanio = tamanio

    def __str__(self):
        return f"{self.nombre} ({self.tamanio} MB)"


class Laptop:
    MAX_APPS = 50

    def __init__(self, modelo, tipo, ancho, alto):
        self.modelo = modelo
        self.apps = []                                # aplicaciones (agregacion)
        self.pantalla = Pantalla(tipo, ancho, alto)   # composicion

    @property
    def cant_apps(self):
        return len(self.apps)

    def agregar_app(self, app):
        if self.cant_apps >= Laptop.MAX_APPS:
            return False
        self.apps.append(app)
        return True

    def eliminar_apps(self):
        self.apps.clear()

    def ordenar_apps(self):
        # peso (tamanio) decreciente; si empatan, por nombre
        self.apps.sort(key=lambda a: (-a.tamanio, a.nombre))

    def mostrar(self):
        print(f"Laptop {self.modelo} | Pantalla: {self.pantalla} | Apps: {self.cant_apps}")
        for a in self.apps:
            print(f"   - {a}")


# b) la laptop con mas aplicaciones (la primera que lo cumpla)
def con_mas_apps(v):
    mayor = v[0]
    for l in v[1:]:
        if l.cant_apps > mayor.cant_apps:
            mayor = l
    return mayor


# c) agregar una aplicacion a la laptop de modelo X
def agregar_app_a_modelo(v, modelo, app):
    for l in v:
        if l.modelo.lower() == modelo.lower():
            if not l.agregar_app(app):
                print(f"La laptop {modelo} ya tiene 50 apps.")


# d) eliminar todas las apps de las laptops con pantalla de ancho Y
def eliminar_apps_por_ancho(v, ancho):
    for l in v:
        if l.pantalla.ancho == ancho:
            l.eliminar_apps()


# e) mostrar todas las laptops que tienen el maximo de apps (cubre empates)
def mostrar_mas_apps(v):
    maximo = con_mas_apps(v).cant_apps
    for l in v:
        if l.cant_apps == maximo:
            l.mostrar()


# f) ordenar las apps de cada laptop
def ordenar_apps_de_todas(v):
    for l in v:
        l.ordenar_apps()


if __name__ == "__main__":
    # a) instanciar N laptops en un vector (lista)
    laptops = [
        Laptop("HP Pavilion", "LED", 1920, 1080),
        Laptop("Dell XPS", "OLED", 2560, 1600),
        Laptop("Lenovo X1", "IPS", 1366, 768),
        Laptop("Asus Zenbook", "OLED", 1920, 1080),
    ]

    laptops[0].agregar_app(Aplicacion("Chrome", 500))
    laptops[0].agregar_app(Aplicacion("Word", 800))
    laptops[0].agregar_app(Aplicacion("Zoom", 300))
    laptops[0].agregar_app(Aplicacion("Excel", 800))

    laptops[1].agregar_app(Aplicacion("Photoshop", 2500))
    laptops[1].agregar_app(Aplicacion("Chrome", 500))

    laptops[2].agregar_app(Aplicacion("Teams", 600))
    laptops[2].agregar_app(Aplicacion("Word", 800))
    laptops[2].agregar_app(Aplicacion("Excel", 800))
    laptops[2].agregar_app(Aplicacion("Spotify", 300))

    laptops[3].agregar_app(Aplicacion("Chrome", 500))

    print("=== a) Laptops creadas ===")
    for l in laptops:
        l.mostrar()

    print("\n=== b) Laptop con mas aplicaciones ===")
    con_mas_apps(laptops).mostrar()

    print("\n=== c) Agregar VLC a la laptop modelo 'Dell XPS' ===")
    agregar_app_a_modelo(laptops, "Dell XPS", Aplicacion("VLC", 150))
    laptops[1].mostrar()

    print("\n=== d) Eliminar apps de laptops con ancho 1920 ===")
    eliminar_apps_por_ancho(laptops, 1920)
    for l in laptops:
        l.mostrar()

    print("\n=== e) Laptop(s) con mas aplicaciones ===")
    mostrar_mas_apps(laptops)

    print("\n=== f) Apps ordenadas (peso desc, empate por nombre) ===")
    ordenar_apps_de_todas(laptops)
    for l in laptops:
        l.mostrar()
