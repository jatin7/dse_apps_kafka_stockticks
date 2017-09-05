:remote close
:remote connect tinkerpop.server conf/remote.yaml session-managed
:remote config timeout max
:remote console
system.graph('s').ifNotExists().create()

:remote config alias g s.g

schema.propertyKey("account_number").Text().single().create()
schema.propertyKey("trade_country").Text().single().create()
schema.propertyKey("cusip").Text().single().create()
schema.propertyKey("quantity").Double().single().create()
schema.propertyKey("type").Text().single().create()
schema.propertyKey("composite_name").Text().single().create()
schema.propertyKey("investment_type").Text().single().create()
schema.propertyKey("purchased").Text().single().create()
schema.propertyKey("inserted").Text().single().create();
schema.propertyKey("price").Double().single().create()
schema.propertyKey("name").Text().single().create()
schema.propertyKey("company").Text().single().create()
schema.propertyKey("currency").Text().single().create()
schema.propertyKey("id").Text().single().create()
schema.propertyKey("product").Text().multiple().create()
schema.propertyKey("symbol").Text().multiple().create()

schema.edgeLabel("owns").multiple().properties("quantity", "purchased").create()
schema.edgeLabel("owns_c").multiple().properties("inserted").create()
schema.edgeLabel("holds").multiple().create()
schema.edgeLabel("asset").multiple().create()
schema.vertexLabel("holding").properties("quantity").create()
schema.vertexLabel("security").properties("symbol","cusip", "price", "currency", "trade_country", "investment_type").create()
schema.vertexLabel("fund").properties("type", "composite_name", "id", "name","product").create()
schema.vertexLabel("sleeve").properties("type", "name", "id","product").create()
schema.vertexLabel("composite").properties("type", "name", "id","product").create()
schema.vertexLabel("account").properties("company", "name", "account_number").create()
schema.edgeLabel("owns_c").connection("account", "composite","sleeve","fund").add()
schema.edgeLabel("owns").connection("account","fund").add()
schema.edgeLabel("holds").connection("fund", "holding").add()
schema.edgeLabel("asset").connection("holding", "security").add()


alexAccount = graph.addVertex(label, 'account', 'name','Alex Rivilis', 'account_number', '1234', 'company', 'DataStax')
Fund51 = graph.addVertex(label, 'fund', 'id', '51', 'name','Transportation', 'type', 'A', 'composite_name', '27','product',['A','FA','FN'])
Fund52 = graph.addVertex(label, 'fund','id', '52',  'name','BioTech', 'type', 'A', 'composite_name', '27','product',['A','CU','FA','FN'])
Fund53 = graph.addVertex(label, 'fund','id', '53',  'name','Financial', 'type', 'A', 'composite_name', '27','product',['A','CU','FA','FN'])
Fund73 = graph.addVertex(label, 'fund','id', '73',  'name','Index 500', 'type', 'A', 'composite_name', '28','product',['A','CU','FA','FN'])
SoftFund51 = graph.addVertex(label, 'fund', 'id', 'S51', 'name','SubTransportation', 'type', 'A', 'composite_name', '27-1','product','CU')


HoldingF51_A = graph.addVertex (label, 'holding', 'quantity', '1471505.54')
HoldingF51_B = graph.addVertex (label, 'holding', 'quantity', '11900')
HoldingF51_C = graph.addVertex (label, 'holding', 'quantity', '60695.55')
HoldingF51_G = graph.addVertex (label, 'holding', 'quantity', '130000')
HoldingF51_H = graph.addVertex (label, 'holding', 'quantity', '1026.45')
HoldingF51_J = graph.addVertex (label, 'holding', 'quantity', '27520')

HoldingF52_G = graph.addVertex (label, 'holding', 'quantity', '210500')
HoldingF52_H = graph.addVertex (label, 'holding', 'quantity', '11001')
HoldingF52_C = graph.addVertex (label, 'holding', 'quantity', '10010101.65')
HoldingF52_I = graph.addVertex (label, 'holding', 'quantity', '14000')
HoldingF52_J = graph.addVertex (label, 'holding', 'quantity', '513')
HoldingF52_K = graph.addVertex (label, 'holding', 'quantity', '31010')

HoldingF53_A = graph.addVertex (label, 'holding', 'quantity', '512360.54')
HoldingF53_B = graph.addVertex (label, 'holding', 'quantity', '15023')
HoldingF53_D = graph.addVertex (label, 'holding', 'quantity', '1134')
HoldingF53_F = graph.addVertex (label, 'holding', 'quantity', '150000')
HoldingF53_J = graph.addVertex (label, 'holding', 'quantity', '1126.45')
HoldingF53_L = graph.addVertex (label, 'holding', 'quantity', '304521')

HoldingF73_A = graph.addVertex (label, 'holding', 'quantity', '215605.54')
HoldingF73_C = graph.addVertex (label, 'holding', 'quantity', '230045.21')
HoldingF73_E = graph.addVertex (label, 'holding', 'quantity', '30000')
HoldingF73_H = graph.addVertex (label, 'holding', 'quantity', '40000')
HoldingF73_K = graph.addVertex (label, 'holding', 'quantity', '1000')
HoldingF73_L = graph.addVertex (label, 'holding', 'quantity', '1500')

cusipA = graph.addVertex (label, 'security', 'symbol', 'F', 'cusip', '38141W273', 'price', '1', 'currency', 'USD', 'trade_country', 'US', 'investment_type', '45')
cusipB = graph.addVertex (label, 'security', 'symbol', 'MSFT', 'cusip', '99ZO13138', 'price', '1.511699', 'currency', 'USD', 'trade_country', 'US', 'investment_type', '61')
cusipC = graph.addVertex (label, 'security', 'cusip', 'USD', 'price', '1', 'currency', 'USD', 'trade_country', 'US', 'investment_type', 'FC')
cusipD = graph.addVertex (label, 'security', 'symbol', 'GE', 'cusip', '99S0RM5C4', 'price', '0', 'currency', 'USD', 'trade_country', 'US', 'investment_type', '54')
cusipE = graph.addVertex (label, 'security', 'symbol', 'ORCL', 'cusip', '99S0ROK74', 'price', '302.011788', 'currency', 'USD', 'trade_country', 'US', 'investment_type', '55')
cusipF = graph.addVertex (label, 'security', 'symbol', 'MCK', 'cusip', 'Y85516AA3', 'price', '96.75', 'currency', 'USD', 'trade_country', 'US', 'investment_type', '30')
cusipG = graph.addVertex (label, 'security', 'symbol', 'BAC', 'cusip', 'X7330ZBB7', 'price', '109', 'currency', 'USD', 'trade_country', 'US', 'investment_type', '10')
cusipH = graph.addVertex (label, 'security','symbol', 'STT', 'cusip', 'U0664NAA1', 'price', '3.0', 'currency', 'USD', 'trade_country', 'US', 'investment_type', '30')
cusipI = graph.addVertex (label, 'security', 'symbol', 'DOW', 'cusip', 'P0607JAE8', 'price', '105.4', 'currency', 'USD', 'trade_country', 'US', 'investment_type', '30')
cusipJ = graph.addVertex (label, 'security', 'symbol', 'DD', 'cusip', 'M10225AA4', 'price', '88.25', 'currency', 'USD', 'trade_country', 'US', 'investment_type', '30')
cusipK = graph.addVertex (label, 'security', 'symbol', 'C', 'cusip', 'G5960L103', 'price', '75.00', 'currency', 'USD', 'trade_country', 'US', 'investment_type', '41')
cusipL = graph.addVertex (label, 'security', 'symbol', 'WFM', 'cusip', 'C9413PAP8', 'price', '96.00', 'currency', 'USD', 'trade_country', 'CA', 'investment_type', 'BL')


Fund51.addEdge ('holds', HoldingF51_A )
Fund51.addEdge ('holds', HoldingF51_B)
Fund51.addEdge ('holds', HoldingF51_C)
Fund51.addEdge ('holds', HoldingF51_G)
Fund51.addEdge ('holds', HoldingF51_H)
Fund51.addEdge ('holds', HoldingF51_J)

SoftFund51.addEdge ('holds', HoldingF51_A )
SoftFund51.addEdge ('holds', HoldingF51_B)
SoftFund51.addEdge ('holds', HoldingF51_C)

HoldingF51_A.addEdge ('asset', cusipA)
HoldingF51_B.addEdge ('asset', cusipB)
HoldingF51_C.addEdge ('asset', cusipC)
HoldingF51_G.addEdge ('asset', cusipG)
HoldingF51_H.addEdge ('asset', cusipH)
HoldingF51_J.addEdge ('asset', cusipJ)


Fund52.addEdge ('holds', HoldingF52_G)
Fund52.addEdge ('holds', HoldingF52_H)
Fund52.addEdge ('holds', HoldingF52_C)
Fund52.addEdge ('holds', HoldingF52_I)
Fund52.addEdge ('holds', HoldingF52_J)
Fund52.addEdge ('holds', HoldingF52_K)

HoldingF52_G.addEdge ('asset', cusipG)
HoldingF52_H.addEdge ('asset', cusipH)
HoldingF52_C.addEdge ('asset', cusipC)
HoldingF52_I.addEdge ('asset', cusipI)
HoldingF52_J.addEdge ('asset', cusipJ)
HoldingF52_K.addEdge ('asset', cusipK)

Fund73.addEdge ('holds', HoldingF73_A)
Fund73.addEdge ('holds', HoldingF73_C)
Fund73.addEdge ('holds', HoldingF73_E)
Fund73.addEdge ('holds', HoldingF73_H)
Fund73.addEdge ('holds', HoldingF73_K)
Fund73.addEdge ('holds', HoldingF73_L)

HoldingF73_A.addEdge ('asset', cusipA)
HoldingF73_C.addEdge ('asset', cusipC)
HoldingF73_E.addEdge ('asset', cusipE)
HoldingF73_H.addEdge ('asset', cusipH)
HoldingF73_K.addEdge ('asset', cusipK)
HoldingF73_L.addEdge ('asset', cusipL)

alexAccount.addEdge ('owns', Fund51)
alexAccount.addEdge ('owns', Fund52)
alexAccount.addEdge ('owns', Fund73)
alexAccount.addEdge ('owns', SoftFund51)
alexAccount.addEdge ('owns', Fund53)

HoldingF53_A.addEdge ('asset', cusipA)
HoldingF53_B.addEdge ('asset', cusipB)
HoldingF53_D.addEdge ('asset', cusipD)
HoldingF53_F.addEdge ('asset', cusipF)
HoldingF53_J.addEdge ('asset', cusipJ)
HoldingF53_L.addEdge ('asset', cusipL)

Fund53.addEdge ('holds', HoldingF53_A)
Fund53.addEdge ('holds', HoldingF53_B)
Fund53.addEdge ('holds', HoldingF53_D)
Fund53.addEdge ('holds', HoldingF53_F)
Fund53.addEdge ('holds', HoldingF53_J)
Fund53.addEdge ('holds', HoldingF53_L)
:remote config alias g s.g

Fund73.addEdge ('holds', HoldingF73_A)
Fund73.addEdge ('holds', HoldingF73_C)
Fund73.addEdge ('holds', HoldingF73_E)
Fund73.addEdge ('holds', HoldingF73_H)
Fund73.addEdge ('holds', HoldingF73_K)
Fund73.addEdge ('holds', HoldingF73_L)

HoldingF73_A.addEdge ('asset', cusipA)
HoldingF73_C.addEdge ('asset', cusipC)
HoldingF73_E.addEdge ('asset', cusipE)
HoldingF73_H.addEdge ('asset', cusipH)
HoldingF73_K.addEdge ('asset', cusipK)
HoldingF73_L.addEdge ('asset', cusipL)

alexAccount.addEdge ('owns', Fund51)
alexAccount.addEdge ('owns', Fund52)
alexAccount.addEdge ('owns', Fund73)



HoldingF53_A.addEdge ('asset', cusipA)
HoldingF53_B.addEdge ('asset', cusipB)
HoldingF53_D.addEdge ('asset', cusipD)
HoldingF53_F.addEdge ('asset', cusipF)
HoldingF53_G.addEdge ('asset', cusipG)
HoldingF53_L.addEdge ('asset', cusipL)

Fund53.addEdge ('holds', HoldingF53_A)
Fund53.addEdge ('holds', HoldingF53_B)
Fund53.addEdge ('holds', HoldingF53_D)
Fund53.addEdge ('holds', HoldingF53_F)
Fund53.addEdge ('holds', HoldingF53_G)
Fund53.addEdge ('holds', HoldingF53_L)

