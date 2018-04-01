	function echartStr(names,brower,boxId){
		// 基于准备好的dom，初始化echarts实例
/*		if (myChart != null && myChart != "" && myChart != undefined) {
	        myChart.dispose();  
	    } */
	    let myChart = echarts.init(document.getElementById(boxId));
	    // 指定图表的配置项和数据
	    var option = {
		    title : {
		        text: '分析一下~',
		        subtext: '嘻嘻',
		        x:'center'
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient: 'vertical',
		        left: 'left',
		        data: names
		    },
		    series : [
		        {
		            name: '访问来源',
		            type: 'pie',
		            radius : '55%',
		            center: ['50%', '60%'],
		            data: brower,
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            },
		            label: {
	                normal: {
	                    show: false,
	                }
	            },
		        }
		    ]
		};
	
	    // 使用刚指定的配置项和数据显示图表。
	    myChart.setOption(option);
	};
	
	//缺陷分类
	function qxfl(num,boxId){
		var brower = [],
			names = [];
		var index = num;
		$.ajax({
	        type: 'get',
	        url: '/analysis/get_pie',//请求数据的地址
            async:true,
	        dataType: "json",        //返回数据形式为json
	        success: function (result) {
	            //请求成功时执行该函数内容，result即为服务器返回的json对象
	            //'result.list' + index 请求json的其中一个
	            //eval() 将对应的字符串解析成JS代码并运行
	            $.each(eval('result.list' + index), function (index, item) {
	                names.push(item.name);    //挨个取出类别并填入类别数组 
	                brower.push({
	                    name: item.name,
	                    value: item.value
	                });
	            });
	            echartStr(names,brower,boxId);
	        },
	        error: function (errorMsg) {
	            //请求失败时执行该函数
	            alert("图表请求数据失败!");
	        }
	    });
	}
	

