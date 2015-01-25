import haxe.macro.Context;
import haxe.macro.Expr;
import haxe.macro.Type;
using haxe.macro.ExprTools;
using haxe.macro.TypeTools;
class Macro
{
	static public inline function getArgsNames(func:Function):Array<Expr>
	{
		var result:Array<Expr> = [for (a in func.args) macro $i{a.name}];
		return result.copy();
	}

	public static function build(e:Expr):Array<Field>
	{
		var fields = Context.getBuildFields();
		var t = Context.getType(e.toString());
		
		switch (t)
		{
			case TInst(ref, p):
				var cl:ClassType = ref.get(); 
				var copy:Field;
				var expr:Expr;
				for (field in cl.fields.get())
				{
					var tDef:TypedExprDef = field.expr().expr;
					switch (tDef)
					{
						case TFunction(func):	

							// var e:Expr = {expr : EConst(CIdent("nativeInstance")), 
								// pos:Context.currentPos()}

							var args:Array<Expr> = [for (a in func.args.copy()) 
								macro $i{a.v.name}];
							if (field.isPublic) args.shift();
							args.unshift(macro $i{"nativeInstance"});

							Sys.println(args);
							
							var funcArgs:Array<FunctionArg> = [];
							var i = 0;
							var fieldName = field.name;
							for (arg in func.args)
							{
								if (i == 0 && field.isPublic)
								{
									i ++;
									continue;
								}

								var cloneArg:FunctionArg = 
								{
									value : null,
									type : arg.v.t.toComplexType(),
									opt : null,
									name : arg.v.name
								};
								funcArgs.push(cloneArg);
								i++;
							}
							var expr = macro nativeTracker.$fieldName;
							var f1:Function =
							{
								ret : null,
								params : [],
								expr : macro ($e{expr})($a{args}),
								args : funcArgs
							};

							var field:Field = 
							{
								pos : Context.currentPos(),
								name : fieldName,
								meta : null,
								kind : FFun(f1),
								access : field.isPublic ? [APublic] : []
							};
							fields.push(field);

						default:
					}
					// Sys.println("\n\n" + field.name);
					// Sys.println(field.expr().expr);
				}

			default:
		}


		return fields;
	}
}
