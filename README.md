# RssSensorsDetect
A app to detect Wifi RSS as well as sensors‘ data.’

###使用说明

点击“开始RSS数据采集”，程序就自动打开wifi，打开传感器，开始循环地记录数据。
中间有个”当前位置“，记录的数据会与这个位置标号联系在一起，可以通过”上一位置“、”下一位置“调整这个位置标号。
点击”关闭RSS数据采集“，这时数据将会存入本地目录"\CIPS-DataCollect"中。
比如"dataRddi_at_2" 存储的是第二个位置上的RSS数据。
"dataBssid.txt"存储的是扫描到的WiFi热点的各种信息，及其顺序。
可以自己将文件导出，然后用matalb等软件进行数据的分析和处理。

数据格式

如果采集了1,2,3，三个位置的数据，目录下的文件为：

dataBssid.txt
dataRssi_at_1.txt
dataRssi_at_2.txt
dataRssi_at_3.txt
第一个文件是bssid列表，每行记录了一个wifi的bssid和对应的序号。

后后三个文件分别是位置为1,2,3时采集的数据，每一行为一个时刻扫描到的数据，后15列的数据分别为：磁场传感器，方向传感器，加速度传感器，陀螺仪，重力传感器（每个传感器的数据包含三个值）。除了后15列的数据，其他的就是wifi的RSSI，第i列的数据对应的是bssid列表中第i个wifi热点的RSSI。

进行完整的一次数据采集实验不能退出程序，一旦退出程序，如果想要更新某一个位置的数据，需要全部重新采集，因为现在每个位置采集的数据都共同使用一个Bssid列表。从程序开启到最后退出的过程中，累计扫描到的wifi个数会越来越多，因此越往后，数据的列数会越多。
