<#import "/spring.ftl" as spring/>

<head>

</head>
<body>
    <#if page??>
    <h1> ${page} </h1>
    </#if>
    <a href="create">Create new</a><br/>
    <a href="/user/index">User List</a>
    <table class="table table-bordered table-hover">
        <tr>
	        <th>Name</th>
	        <th>Price</th>
        </tr>
        <#list items as item>
            <tr>
                <td>${item.name}</td>
                <td>${item.price} €</td>
                <td><a href="details/${item["id"]}">Detail</a></td>
                <td>
                    <form action="delete" method="POST">
                    <input type="hidden" name="id" value="${item["id"]}">
                    <input type="submit" value="delete"/>
                    </form>
                </td>
            </tr>
        </#list>
    </table>
</body>
