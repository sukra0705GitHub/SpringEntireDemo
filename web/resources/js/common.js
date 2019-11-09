/*jQuery(document).ready(function(){
    jQuery("#submit").click(function (){
        var dirPath = jQuery("#dirPath").val();
        var nameLike = jQuery("#nameLike").val();
        if (!dirPath || !nameLike){
            !dirPath && jQuery("#tipDirPath").html("*Enter Directory Path Please!");
            !nameLike && jQuery("#tipNameLike").html("*Enter Name Like Please!");
        } else {
            jQuery("#tipDirPath").html("*");
            jQuery("#tipNameLike").html("*");
            var data = {
                dirPath: dirPath,
                nameLike: nameLike
            };
            jQuery.get("http://172.31.162.193:8080/search",data,function(response, status){
              console.log("response:" + response);
              console.log("status:" + status);
            });
        }
    })
});*/

jQuery(document).ready(function(){
    jQuery("#submit").click(function (){
        var dirPath = jQuery("#dirPath").val();
        var nameLike = jQuery("#nameLike").val();
        var data = {
            dirPath: dirPath,
            nameLike: nameLike
        };
        jQuery.get("http://172.31.162.193:8080/search",data,function(response, status){
          console.log("response:" + response);
          console.log("status:" + status);
        });
    })
});

jQuery(function () {
  jQuery('[data-toggle="popover"]').popover();
  jQuery('.dropdown-toggle').dropdown()
})

