<!DOCTYPE html>
<html lang="en">
    <head>
        <style>
            #chart {
                padding: 0;
                max-width: 380px;
                margin: 35px auto;
            }
        </style>

        <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
        <script src="https://cdn.jsdelivr.net/npm/vue-apexcharts"></script>
    </head>
    <body>
        <div id="app">
            <div id="chart">
                <apexchart type="pie" width="380" :options="chartOptions" :series="series"></apexchart>
            </div>
        </div>
        <script>
            new Vue({
                el: '#app',
                components: {
                    apexchart: VueApexCharts,
                },
                data: {

                    series: [44, 55, 13, 43, 22],
                    chartOptions: {
                        chart: {
                            width: 380,
                            type: 'pie',
                        },
                        labels: ['Team A', 'Team B', 'Team C', 'Team D', 'Team E'],
                        responsive: [{
                                breakpoint: 480,
                                options: {
                                    chart: {
                                        width: 200
                                    },
                                    legend: {
                                        position: 'bottom'
                                    }
                                }
                            }]
                    },
                },
            })
        </script>
    </body>
</html>
