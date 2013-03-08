(function($) {
    var __bind = function(fn, me){ return function(){ return fn.apply(me, arguments); }; };
    $.widget("ui.match", {
        options: {
            
        },
        defaults: {
            radius:0,
            clear: null,
            width: 400,
            height: 300,
            options: [],
            responses: [],
            joins:null,
            bendmodidicator:90
        },
        current:null,
        currentjoin:null,
        currentXMouse:0,
        currentYMouse:0,
        down:false,
        _create: function() {
            this.options = $.extend(true, {}, this.defaults, this.options);
            if( this.options.joins == null)
                this.options.joins = Array();
            var id = this.element.attr("id");
            this.element.append("<canvas id='canvas" + id + "' width=" + this.options.width + " height=" + this.options.height + "></canvas>");
            var canvas = document.getElementById("canvas" + id);
           
            var ctx = null;
            if (canvas.getContext) 
                ctx = canvas.getContext("2d");
            for (var a = 0; a < this.options.options.length; a++) {
                    var item = this.options.options[a];
                    item.x = 10;
                    item.y = a*32;
                    var width = ctx.measureText(item.text).width;
                    item.width = 100;
                    if(width > item.width - 10)
                        item.width = width + 10;
                    item.height = 28;
            }
            var maxWidth = 100;
            for (var a = 0; a < this.options.responses.length; a++) {
                var item = this.options.responses[a];
                item.x = 310;
                item.y = a*32;
                var width = ctx.measureText(item.text).width;
                item.width = 100;
                if(width > item.width - 10)
                    item.width = width + 10;
                if(item.width > maxWidth)
                    maxWidth = item.width;
                item.height = 28;
            }
            
            for (var a = 0; a < this.options.joins.length; a++) {
                var item = this.options.joins[a];
                var question = jQuery.grep(this.options.options, function(value) {
                    return value.id == item.question.id;
                });
                
                var response = jQuery.grep(this.options.responses, function(value) {
                    return value.id == item.response.id;
                });

                if(question.length==1 && response.length==1)
                {
                    item.question = question[0];
                    item.response = response[0];
                }
            }
                
            for (var a = 0; a < this.options.responses.length; a++) {
                var item = this.options.responses[a];
                item.x = this.options.width - maxWidth - 10;
            }
            var id = this.element.attr("id");
            var canvas = document.getElementById("canvas" + id);
            
            $(canvas).mousedown(__bind(function(event) {
                return this.Mousedown(event.pageX, event.pageY);
            }, this));
          
            $(canvas).mouseup(__bind(function(event) {
                return this.Mouseup(event.pageX, event.pageY);
            }, this));
            
            $(canvas).mousemove(__bind(function(event) {
                return this.Mousemove(event.pageX, event.pageY);
            }, this));
          
    
            this._render();
          

        },
        _render: function() {
            var id = this.element.attr("id");
            var canvas = document.getElementById("canvas" + id);
            if (canvas.getContext) {
                var ctx = canvas.getContext("2d");
                ctx.clearRect(0, 0, this.options.width, this.options.height);
               
                var startAngle     = 0;
    	        var endAngle       = Math.PI*2;

                for (var a = 0; a < this.options.options.length; a++) {
                    var item = this.options.options[a];
                    this._renderQuestion(item,ctx);
                }
                for (var a = 0; a < this.options.responses.length; a++) {
                    var item = this.options.responses[a];
                    this._renderAnswer(item,ctx);
                }
                for (var a = 0; a < this.options.joins.length; a++) {
                    var item = this.options.joins[a];
                    this._renderJoin(item,ctx);
                }
                
                if( this.currentjoin != null)
                {
                    this._renderJoin(this.currentjoin,ctx);
                }
               
            }
        },
        _renderQuestion: function(item,ctx)
        {
            ctx.beginPath();
            ctx.roundRect(item.x , item.y, item.x + item.width, item.y + item.height, this.options.radius);
            var grd = ctx.createLinearGradient(item.x , item.y, item.x+item.width, item.y+item.height);
            grd.addColorStop(0, "#FFFFFF");
            grd.addColorStop(1, "#E6E6E6");
            ctx.fillStyle = grd;
            ctx.fill();
            ctx.lineWidth = 1;
            ctx.fillStyle = "#333333";
             
            ctx.textAlign = "left";
            ctx.fillText(item.text, item.x + 5, item.y + (item.height/2) + 4);
           
            ctx.closePath();
            
        },
         _renderAnswer: function(item,ctx)
        {
            ctx.beginPath();
           
            ctx.roundRect(item.x , item.y, item.x + item.width, item.y + item.height, this.options.radius);
            var grd = ctx.createLinearGradient(item.x , item.y, item.x+item.width, item.y+item.height);
            grd.addColorStop(1, "#FFFFFF");
            grd.addColorStop(0, "#E6E6E6");
            ctx.fillStyle = grd;
            ctx.fill();
            ctx.lineWidth = 1;
            ctx.fillStyle = "#333333";
             
            ctx.textAlign = "left";
            ctx.fillText(item.text, item.x + 5, item.y + (item.height/2) + 4);
            ctx.closePath();
            
        },
        _joinItems:function(q,a)
        {
            var join = {question:q,response:a};
            return join;
        },
        _renderJoin: function(item,ctx)
        {
            ctx.beginPath();
            var bendmod = this.options.bendmodidicator;
            var controlX1,controlY1,controlX2,controlY2;
            controlX1 = controlY1 = controlX2 = controlY2 = 0;
            if(item.question != null)
                ctx.moveTo(item.question.x + item.question.width , item.question.y+(item.question.height/2));
            else
                ctx.moveTo(item.x  , item.y);
            
            if(item.question != null)
            {
                controlX1 = item.question.x + item.question.width + bendmod;
                controlY1 = item.question.y + (item.question.height/2);
            }
            else
            {
                controlX1 = item.x + bendmod;
                controlY1 = item.y;
            }
            if(item.response != null)
            {
                controlX2 = item.response.x  - bendmod;
                controlY2 = item.response.y + (item.response.height/2);
            }
            else
            {
                controlX2 = item.x - bendmod;
                controlY2 = item.y;
            }
            var endX,endY;
            endX = endY = 0;
            if(item.response != null)
            {
                endX = item.response.x;
                endY = item.response.y + (item.response.height/2);
            }
            else
            {
                endX = item.x;
                endY = item.y;
            }
                
            ctx.bezierCurveTo(controlX1, controlY1, controlX2,controlY2, endX, endY);

            ctx.lineWidth = 1;
            if(item.over == true)
                ctx.strokeStyle = "red"; // line color
            else
                ctx.strokeStyle = "black"; // line color

            ctx.stroke();
            ctx.closePath();
            
        },
        _detectJoin: function(item,ctx,x,y)
        {
            ctx.beginPath();
            var bendmod = this.options.bendmodidicator;
            var controlX1,controlY1,controlX2,controlY2;
            controlX1 = controlY1 = controlX2 = controlY2 = 0;
            if(item.question != null)
                ctx.moveTo(item.question.x + item.question.width , item.question.y+(item.question.height/2));
            else
                ctx.moveTo(item.x  , item.y);
            
            if(item.question != null)
            {
                controlX1 = item.question.x + item.question.width + bendmod;
                controlY1 = item.question.y + (item.question.height/2);
            }
            else
            {
                controlX1 = item.x + bendmod;
                controlY1 = item.y;
            }
            if(item.response != null)
            {
                controlX2 = item.response.x  - bendmod;
                controlY2 = item.response.y + (item.response.height/2);
            }
            else
            {
                controlX2 = item.x - bendmod;
                controlY2 = item.y;
            }
            var endX,endY;
            endX = endY = 0;
            if(item.response != null)
            {
                endX = item.response.x;
                endY = item.response.y + (item.response.height/2);
            }
            else
            {
                endX = item.x;
                endY = item.y;
            }
                
            ctx.bezierCurveTo(controlX1, controlY1, controlX2,controlY2, endX, endY);
            ctx.lineWidth = 1;
            ctx.strokeStyle = "black"; // line color
            
            if (ctx.isPointInPath(x,y))
            {
                ctx.closePath();
                return true;
            }
            else
            {
                 ctx.closePath();
                 return false;
            }
                
        },
        _setOption: function(key, value) {

            switch (key) {
                case "clear":
                    break;
            }
            $.Widget.prototype._setOption.apply(this, arguments);
            this._super("_setOption", key, value);
        },
        Mousemove: function(x, y) {
            var current, pos, posx, posy;
            
            //track the position of the cursor 
            var id = this.element.attr("id");
            pos = $("#canvas" + id).offset();
            posx = x - pos.left;
            posy = y - pos.top;
            this.currentXMouse = posx;
            this.currentYMouse = posy;
            var mouseOver = false;    
            if(this.down)
            {
                if(this.currentjoin != null)
                {
                    this.currentjoin.x = posx;
                    this.currentjoin.y = posy;
                    //this.refresh();
                }
            }
            else
            {
                var id = this.element.attr("id");
                var canvas = document.getElementById("canvas" + id);
                if (canvas.getContext) {
                    var ctx = canvas.getContext("2d");
                     for (var a = 0; a < this.options.joins.length; a++) {
                        var item = this.options.joins[a];
                        item.over = this._detectJoin(item,ctx,posx,posy);
                        if(item.over)
                            mouseOver = true;
                    }
                }
            }
            //if(mouseOver)
            this.refresh();
            
            return this;
        },
        Mouseup: function(x,y) {
            var current, pos, posx, posy;
            var id = this.element.attr("id");
          
            pos = $("#canvas" + id).offset();
            posx = x - pos.left;
            posy = y - pos.top;
          
           
            current = this.GetSelectedItem(posx, posy,this.options.options);
            if(current == null)
            {
                current = this.GetSelectedItem(posx, posy,this.options.responses);
                this.currentjoin.response = current;
            } else {
               this.currentjoin.question = current;
            }
            this.down = false;
            if( this.currentjoin.response && this.currentjoin.question)
            {
               this.options.joins.push(this.currentjoin);
            }
            this.currentjoin = null;
            this.refresh();
            return this;
        },
        Mousedown: function(x, y) {
            var current, pos, posx, posy;
            
            var id = this.element.attr("id");
            pos = $("#canvas" + id).offset();
            
            posx = x - pos.left;
            posy = y - pos.top;
                    
            var itemToRemove = null;
            for (var a = 0; a < this.options.joins.length; a++) {
                var item = this.options.joins[a];
                if(item.over)
                    itemToRemove = item;
            }
            if(itemToRemove != null)
            {
                this.options.joins = jQuery.grep(this.options.joins, function(value) {
                    return value.over == false;
                });
            }
                //this.options.joins.remove(itemToRemove);
            
            this.currentjoin = this._joinItems(null,null);
            current = this.GetSelectedItem(posx, posy,this.options.options);
            if(current == null)
            {
                current = this.GetSelectedItem(posx, posy,this.options.responses);
                this.currentjoin.response = current;
            }
            if (!(current != null)) {
                this.down = false;
                return this;
            } else {
                this.current = current;
                if(this.currentjoin.response == null)
                    this.currentjoin.question = current;
                this.down = true;
            }
           
          
            return this;
        },
         GetSelectedItem: function(x, y, options) {
          var opt, ret, _fn, _i, _len, _ref;
          ret = null;
          _ref = options;
          _fn = __bind(function(opt) {
            if (x > (opt.x) & x < (opt.x + opt.width)) {
              if (y > (opt.y) & y < (opt.y + opt.height)) {
                if (ret === null) {
                  return ret = opt;
                }
              }
            }
          }, this);
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            opt = _ref[_i];
            _fn(opt);
          }
          return ret;
        },
        getValues: function(){
            return this.options.joins;
        },
        clear: function(){
            this.options.joins = Array();
            this._render();
        },
        refresh: function() {
            this._render();
        },
        destroy: function() {
            $.Widget.prototype.destroy.call(this);
        }
    });
} (jQuery));
//http://js-bits.blogspot.com/2010/07/canvas-rounded-corner-rectangles.html
CanvasRenderingContext2D.prototype.roundRect = function(sx, sy, ex, ey, r) {
    var r2d;
    r2d = Math.PI / 180;
    if ((ex - sx) - (2 * r) < 0) {
      r = (ex - sx) / 2;
    }
    if ((ey - sy) - (2 * r) < 0) {
      r = (ey - sy) / 2;
    }
    this.beginPath();
    this.moveTo(sx + r, sy);
    this.lineTo(ex - r, sy);
    this.arc(ex - r, sy + r, r, r2d * 270, r2d * 360, false);
    this.lineTo(ex, ey - r);
    this.arc(ex - r, ey - r, r, r2d * 0, r2d * 90, false);
    this.lineTo(sx + r, ey);
    this.arc(sx + r, ey - r, r, r2d * 90, r2d * 180, false);
    this.lineTo(sx, sy + r);
    this.arc(sx + r, sy + r, r, r2d * 180, r2d * 270, false);
    return this.closePath();
  }
  