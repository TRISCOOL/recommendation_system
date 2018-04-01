function indexFn() {
  require(['jquery', 'echarts'], function($, echarts) {
    console.log(echarts)
      $(function() {
          buildData();
      });

      function buildData() {
          //定义数据结构
          $.ajax({
              type:"get",
              url:"/analysis/tx",
              async:true,
              success:function (response) {
                  console.log(response);
                  let sexTx = getData(['男','女'],[response.maleCount,response.femaleCount]);
                  let typeTx = getData(['0-18','18-23','24-29','30-35','36-100'],[response.firstCount,
                      response.secondCount,response.thirdCount,response.frouthCount,response.fiveCount]);

                  canvasEcharts(sexTx,'first','不同性别对不同类型电影的喜欢程度');
                  canvasEcharts(typeTx,'second','不同年龄段对不同类型电影的喜欢程度');
              }
          });
      }

      function getData(xAxis,value,desc) {
          var datas = {
              'colors': ['#006699', '#4cabce','green','black'],
              'xAxis': xAxis,
              'legend':['动作','爱情','恐怖','喜剧'],
              'list': [{
                  'title': desc,
                  'series': value,
                  'elid': 'first'
              }]
          };
          return datas;
      }

      function canvasEcharts(json, boxId) {
          var obj = json['list'][0];
          var myChats = echarts.init(document.getElementById(boxId));
          var option = {
              title: {
                  text: obj['title'],
                  // subtext: index==0?'数据来自投票结果而时时变化':''  //只有第一个要副标题
                  //主标题文本超链接
                  //link: 'http://www.xxxxxxxxxx',
                  subtext: '根据用户行为获得此结论'
              },
              color: ['#006699', '#4cabce', '#e5323e'],
              tooltip: {
                  trigger: 'axis',
                  axisPointer: {
                      type: 'shadow'
                  }
              },
              toolbox: {
                  //显示策略，可选为：true（显示） | false（隐藏），默认值为true
                  show: true,
                  //垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）
                  y: 'center',
                  feature: {
                      //saveAsImage，保存图片（IE8-不支持）,图片类型默认为'png'
                      saveAsImage: { show: true }
                  }
              },
              legend: {
                  left: "70%",
                  data: ['好', '中', '差']
              },
              grid: {
                  left: '3%',
                  right: '4%',
                  bottom: '3%',
                  containLabel: true,
              },
              xAxis: [{
                  min: 0,
                  //坐标轴类型，横轴默认为类目型'category'
                  type: 'category',
                  data: json['xAxis']
              }],
              yAxis: [{
                  min: 0,
                  max: 60,
                  //坐标轴类型，纵轴默认为数值型'value'
                  type: 'value'
              }],
              series: (function(){
                  var arr=[];
                  for (var i = 0; i < obj['series'].length; i++) {
                      var thisobj={
                          name: json['legend'][i],
                          type: 'bar',
                          barWidth: '15%',
                      };
                      thisobj.data=obj['series'][i];
                      arr.push(thisobj)
                  }
                  return arr
              })()
          };
          //为echarts对象加载数据
          myChats.setOption(option, true);
      }

  });
}

require(['config'], indexFn);