        var type = 0;
        var q;
        var word=[];
        var z=0;
        function rand(m) {
            return Math.random() * m;
        }
        function matrix_getChar(type) {
            if (type == 0) {
                if (rand(2) > 1)
                    return 0x31F0 + rand(16);
                return 0x3041 + rand(85);            
            } 
            else if (type == 1)
                return 48 + rand(2);
            else if (type == 2) {
                if (rand(2) > 1)
                    return 0x61 + rand(26);
                else if (rand(2) > 1)
                    return 0x41 + rand(26);
                else
                    return 0x30 + rand(10);
            }
            else if (type == 3)
                return 30000 + rand(33);
            else if(type==6) if(z!=1) return word[Math.round(rand(z-1))] 
                             else return word[0] ;
            else return 33 + rand(94);
                
        }
        function matrix_onLoad(s) {
            var canvas = document.getElementById("matrix_canvas");
            q = canvas.getContext('2d');
            document.body.addEventListener("keypress", matrix_onKeyPress, false);
            
            for (var c = [], p = [], i = 0; i < 550; ++i) {
                p[i] = rand(1000);
                c[i] = ' ';
            }   
            var w = canvas.width = window.screen.width*2;
            var h = canvas.height = window.screen.height*2;
            q.fillStyle = 'rgba(0,0,0,1)';
            q.fillRect(0, 0, w, h);
            var f = function () {
                q.fillStyle = 'rgba(0,0,0,.05)';
                q.fillRect(0, 0, w, h);
                q.font = "17px consolas";
                p.map(function(v, i) {
                    q.fillStyle = 'rgba(0,0,0,1)';
                    q.fillRect(i*18, v-18, 18, 18);
                                        
                    q.fillStyle = '#4D4';
                    q.fillText(c[i], i * 18, v-18);
                    
                    c[i] = String.fromCharCode(matrix_getChar(type));
                    q.fillStyle = '#FFF';
                    q.fillText(c[i], i * 18, v);
                    p[i] = v > 450 + rand(10000) ? 0 : v + 18;
                });
                
            }
            setInterval(f, 33);
        }
        function matrix_onKeyPress(e) {
            if (e.keyCode == 32 || e.keyCode == 13){
                type = (type + 1) % 5;word=[];z=0;}
            else {type=6;word[z]=e.keyCode;z=z+1}    
        }