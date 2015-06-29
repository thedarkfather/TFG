jQuery(document).ready(function () {

    
    jQuery.waitForImages.hasImgProperties = ['background','backgroundImage'];
    jQuery('body').waitForImages(function() {
        jQuery(".page-mask").delay(1200).fadeOut('slow');
        jQuery('body').css('overflowY','auto');
    });


/*-------------------------------------------------*/
/* =  Animated content
/*-------------------------------------------------*/

    wow = new WOW(
        {
            animateClass: 'animated',
            offset:       100
        }
    );

    wow.init();

/*==========================*/
/* Sticky Navigation
/*==========================*/
     
    jQuery("#navigation").sticky({topSpacing:0});

    
/*==========================*/
/* Video Background Overlay
/*==========================*/

    var winheight = jQuery(window).height();

    jQuery(".video-overlay").css( "height", winheight );


/* ==============================================
Drop Down Menu Fade Effect
=============================================== */  

    $('.nav-toggle').hover(function() {
        'use strict';
        $(this).find('.dropdown-menu').first().stop(true, true).slideDown(250);
        }, function() {
        $(this).find('.dropdown-menu').first().stop(true, true).slideUp(250)
     });







    


    

/*----------------------------------------------------*/
/*  Parallax section
/*----------------------------------------------------*/
    //Calculating page width
    pageWidth = jQuery(window).width();

    //Parallax  
    jQuery(window).bind('load', function () {
        if(pageWidth > 980) {
            parallaxInit();
        }                       
    });

    function parallaxInit() {
        jQuery('.landing-left').parallax("30%", 0.1);
        jQuery('.testimonial-wrap').parallax("30%", 0.1);
        jQuery('.quote-wrap').parallax("30%", 0.1);
        jQuery('.subscription-wrap').parallax("30%", 0.1);
        jQuery('.image-parallax').parallax("50%", 0.1);
        
    }


    //Horizontal parallax
    jQuery('.about-wrap .parallax-layer')
        .hparallax({
          mouseport: jQuery('.about-wrap')
    }); 





/*----------------------------------------------------*/
/*  Animated Count To
/*----------------------------------------------------*/

    jQuery('.fun-wrap .fun-box').each(function () {
        jQuery(this).fappear(function() {
            jQuery('.fun').countTo();
        }); 
    });


 
/*----------------------------------------------------*/
/*  Scroll To Top Section
/*----------------------------------------------------*/
    jQuery(document).ready(function () {
    
        jQuery(window).scroll(function () {
            if (jQuery(this).scrollTop() > 100) {
                jQuery('.scrollup').fadeIn();
            } else {
                jQuery('.scrollup').fadeOut();
            }
        });
    
        jQuery('.scrollup').click(function () {
            jQuery("html, body").animate({
                scrollTop: 0
            }, 600);
            return false;
        });
    
    });







/*----------------------------------------------------*/
/*  PrettyPhoto
/*----------------------------------------------------*/

    jQuery(function(){
        jQuery("a[data-gal^='prettyPhoto']").prettyPhoto({
              opacity: 0.5,
              social_tools: "",
              deeplinking: false
        });

        jQuery('a[data-rel^="prettyPhoto"]').prettyPhoto();
    });     



    jQuery("#horizontal-tabs").tytabs({
        tabinit: "1",
        fadespeed: "fast"
    });


});




/*----------------------------------------------------*/
/*  BxSlider
/*----------------------------------------------------*/


jQuery(document).ready(function(){
    
    var onMobile = false;
    if( /Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent) ) { onMobile = true; }
    
    jQuery('.fullwidth-slider').bxSlider({
        mode: "fade",
        speed: 1000,
        pager: false,
        nextText: '<i class="fa fa-angle-right"></i>',
        prevText: '<i class="fa fa-angle-left"></i>', 
        
        onSlideBefore: function($slideElement) {
            ($slideElement).find('.slide-caption').fadeOut().animate({top:'100px'},{queue:false, easing: 'easeOutQuad', duration: 550});
            ($slideElement).find('.slide-caption').hide().animate({top:'-100px'});
        },
        onSlideAfter: function($slideElement) {
            ($slideElement).find('.slide-caption').fadeIn().animate({top:'0'},{queue:false, easing: 'easeOutQuad', duration: 450});
        },
        
    });
    
    jQuery('.bx-wrapper .bx-controls-direction a').attr('data-500','top:83%; opacity: 0;').attr('data-start','top:50%; opacity: 1;');
    
    
    if( ( onMobile === false ) && ( jQuery('.parallax-slider').length ) ) {
    
        skrollr.init({
            edgeStrategy: 'set',
            smoothScrolling: false,
            forceHeight: false
        });
        
    }


    jQuery('.text-slide').bxSlider({
        controls: false,
        adaptiveHeight: true, 
        pager: false,       
        auto:true,
        mode:'fade',
        pause: 3000,
    });   

    
});  
