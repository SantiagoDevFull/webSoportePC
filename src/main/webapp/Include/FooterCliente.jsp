<%-- 
    Document   : FooterCliente
    Created on : 20 jul. 2023, 18:02:03
    Author     : usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<script>
    window.addEventListener('mouseover', initLandbot, {once: true});
    window.addEventListener('touchstart', initLandbot, {once: true});
    var myLandbot;
    function initLandbot() {
        if (!myLandbot) {
            var s = document.createElement('script');
            s.type = 'text/javascript';
            s.async = true;
            s.addEventListener('load', function () {
                var myLandbot = new Landbot.Livechat({
                    configUrl: 'https://storage.googleapis.com/landbot.site/v3/H-1274553-7DJHY7GQY4X2Z5YU/index.json',
                });
            });
            s.src = 'https://cdn.landbot.io/landbot-3/landbot-3.0.0.js';
            var x = document.getElementsByTagName('script')[0];
            x.parentNode.insertBefore(s, x);
        }
    }
</script>

<style>
    /* Estilo personalizado para justificar el texto */
    .custom-text-justify {
        text-align: justify;
        text-justify: inter-word; /* Esta propiedad es para mayor compatibilidad con navegadores */
    }
</style>
<footer class="bg-dark text-light py-4">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <!-- Aquí puedes insertar el logo de tu empresa o sitio web -->
                <img src="http://localhost/img/logoEmpresa.jpg"  class="img-fluid rounded" width="80%">
            </div>
            <div class="col-md-4 ">
                <h5>Misión</h5>
                <p class="custom-text-justify container">
                    Ser el proveedor líder en soluciones tecnológicas para empresas y particulares, 
                    ofreciendo una amplia gama de productos y servicios relacionados con computadoras, 
                    impresoras y servicios de reparación. Buscamos brindar a nuestros clientes la mejor 
                    experiencia en tecnología, superando sus expectativas mediante productos de calidad, 
                    atención personalizada y soluciones efectivas para sus necesidades informáticas.
                </p>
                <h5>Visión</h5>
                <p class="custom-text-justify container">
                    Convertirnos en el referente en el mercado de tecnología y servicios informáticos 
                    en nuestra región. Aspiramos a ser reconocidos por nuestra excelencia en la calidad 
                    de nuestros productos, la eficiencia de nuestros servicios de reparación y la innovación 
                    en soluciones tecnológicas. Buscamos construir relaciones duraderas con nuestros clientes, 
                    basadas en la confianza y la satisfacción, mientras nos esforzamos por ser un motor de 
                    progreso en la adopción de tecnologías avanzadas que mejoren la vida de las personas y 
                    contribuyan al crecimiento de las empresas. Nuestra visión incluye expandir nuestra cobertura 
                    para llegar a más comunidades y ser reconocidos como un aliado confiable en el ámbito tecnológico.
                </p>
            </div>
            <div class="col-md-4">
                <h5 class="bg-danger text-center rounded p-2">Contacto</h5>
                <p><h6 class="bg-primary p-1">Dirección:</h6><i class="fa-solid fa-location-dot fa-lg" style="color: #e00022;"></i><a target="blank_" href="https://www.google.com/maps/place/Av.+Uruguay+319,+Lima+15001/@-12.0543294,-77.041329,17z/data=!3m1!4b1!4m6!3m5!1s0x9105c8c670b8e715:0xac47932d5396d729!8m2!3d-12.0543294!4d-77.0387541!16s%2Fg%2F11c1xg3n9w?entry=ttu"> Av. Uruguay 319 - Cercado de Lima</a></p>
                <p><h6 class="bg-primary p-1">Teléfono:</h6><i class="fa-solid fa-phone fa-lg" style="color: #55e208;"></i> 981 812 422</p>
                <p><h6 class="bg-primary p-1">Email:</h6><i class="fa-solid fa-envelope fa-lg" style="color: #ec09e5;"></i> atscomputer3000@gmail.com</p>
            </div>
        </div>
    </div>
    <div class="bg-secondary py-2">
        <div class="container">
            <p class="mb-0 text-center">Copyrigth &copy; 2023 R y J Computer S.A.C.</p>
        </div>
    </div>
</footer>
