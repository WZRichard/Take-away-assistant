# Take-away assistant （外卖助手）

> 代码详见：./Java-take-away-assistant & 相关库文件：./Java-take-away-assistant/lib

### Environment：MYSQL5.7 & java11.0 & Java FX

1. 详情描述：每下一单都只能集那个商家的一种券一次；默认订单要求送达时间为1h后；干到10单成为正式员工，单数>500为单王

2. 用户界面：
创建、登录、下单（下单后要更新order_、order_detail、集券表）、查看优惠券集单情况、评价（商家名、菜品名、送达时间、评价...）、配送地址

3. 商家界面：
创建、登录、添加商品种类（表merchandise_sort受影响）、上传商品（表belong_to， merchandise_information受影响）、设置优惠券（表discount_coupon受影响）、设置集单（表full_reduction受影响）、查看订单（订单号、顾客名、菜品名、数量、状态、骑手名、开始时间、截止时间）

4. 骑手界面：
创建、登录、抢单、配送（地址）、总s收入

5. 管理员界面：
创建、登录、所有商家骑手用户信息、用户会员